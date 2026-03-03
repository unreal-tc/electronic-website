<!--
  文件名称：checkout.vue
  描述：结算页面，支持从购物车结算和直接购买

  @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
  @since 2026-03-03
  @see stores/cart.ts
-->

<!-- AI-GENERATED-BEGIN -->
<!-- GENERATED-BY-Claude -->
<template>
  <div class="max-w-4xl mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold mb-6">确认订单</h1>

    <div v-if="!authStore.isLoggedIn" class="text-center py-20">
      <p class="text-gray-500">请先登录</p>
      <NuxtLink to="/login?redirect=/checkout" class="text-primary hover:underline">去登录</NuxtLink>
    </div>

    <div v-else-if="orderItems.length === 0" class="text-center py-20">
      <p class="text-5xl mb-4">📦</p>
      <p class="text-gray-500 mb-4">没有待结算的商品</p>
      <NuxtLink to="/products" class="text-primary hover:underline">去选购</NuxtLink>
    </div>

    <div v-else>
      <div class="bg-white rounded-xl shadow-sm border p-6 mb-6">
        <h2 class="text-lg font-semibold mb-4">商品清单</h2>
        <div class="divide-y">
          <div v-for="item in orderItems" :key="item.productId" class="flex items-center gap-4 py-3">
            <img :src="item.imageUrl" :alt="item.name" class="w-16 h-16 object-cover rounded-lg">
            <div class="flex-1">
              <p class="font-medium">{{ item.name }}</p>
              <p class="text-sm text-gray-500">¥{{ item.price.toFixed(2) }} × {{ item.quantity }}</p>
            </div>
            <p class="text-primary font-bold">¥{{ (item.price * item.quantity).toFixed(2) }}</p>
          </div>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-sm border p-6 mb-6">
        <h2 class="text-lg font-semibold mb-4">收货信息</h2>
        <div class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">收货人</label>
            <input
              v-model="receiverName"
              type="text"
              placeholder="请输入收货人姓名"
              class="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
            >
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">联系电话</label>
            <input
              v-model="receiverPhone"
              type="tel"
              maxlength="11"
              placeholder="请输入联系电话"
              class="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
            >
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">收货地址</label>
            <textarea
              v-model="receiverAddress"
              placeholder="请输入详细收货地址"
              rows="2"
              class="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent resize-none"
            />
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">备注（选填）</label>
            <input
              v-model="remark"
              type="text"
              placeholder="有什么要告诉卖家的？"
              class="w-full border border-gray-300 rounded-lg px-4 py-2.5 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
            >
          </div>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-sm border p-6 flex items-center justify-between">
        <div>
          <span class="text-gray-600">共 {{ totalQuantity }} 件商品，</span>
          <span class="text-gray-600">合计：</span>
          <span class="text-primary text-2xl font-bold">¥{{ totalPrice.toFixed(2) }}</span>
        </div>
        <button
          class="bg-primary text-white px-10 py-3 rounded-lg text-lg font-semibold hover:opacity-90 transition disabled:opacity-50"
          :disabled="submitting"
          @click="submitOrder"
        >
          {{ submitting ? '提交中...' : '提交订单' }}
        </button>
      </div>

      <p v-if="errorMsg" class="text-red-500 text-center mt-4">{{ errorMsg }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '~/stores/auth'
import { useCartStore } from '~/stores/cart'

interface CheckoutItem {
  productId: number
  name: string
  imageUrl: string
  price: number
  quantity: number
}

const authStore = useAuthStore()
const cartStore = useCartStore()
const route = useRoute()
const router = useRouter()
const config = useRuntimeConfig()

const orderItems = ref<CheckoutItem[]>([])
const receiverName = ref('')
const receiverPhone = ref('')
const receiverAddress = ref('')
const remark = ref('')
const submitting = ref(false)
const errorMsg = ref('')

const isDirectBuy = computed(() => !!route.query.productId)
const cartItemIds = computed(() => {
  const ids = route.query.cartItems as string
  return ids ? ids.split(',').map(Number) : []
})

const totalQuantity = computed(() => orderItems.value.reduce((s, i) => s + i.quantity, 0))
const totalPrice = computed(() => orderItems.value.reduce((s, i) => s + i.price * i.quantity, 0))

onMounted(async () => {
  if (!authStore.isLoggedIn) return

  if (isDirectBuy.value) {
    const productId = Number(route.query.productId)
    const quantity = Number(route.query.quantity) || 1
    try {
      const result = await $fetch<{ code: number, data: { id: number, name: string, imageUrl: string, price: number } }>(
        `${config.public.apiBase}/api/products/${productId}`,
      )
      if (result.data) {
        orderItems.value = [{
          productId: result.data.id,
          name: result.data.name,
          imageUrl: result.data.imageUrl,
          price: result.data.price,
          quantity,
        }]
      }
    }
    catch {
      errorMsg.value = '获取商品信息失败'
    }
  }
  else if (cartItemIds.value.length > 0) {
    await cartStore.fetchCart()
    orderItems.value = cartStore.items
      .filter(i => cartItemIds.value.includes(i.id))
      .map(i => ({
        productId: i.product.id,
        name: i.product.name,
        imageUrl: i.product.imageUrl,
        price: i.product.price,
        quantity: i.quantity,
      }))
  }
})

async function submitOrder() {
  errorMsg.value = ''
  if (!receiverName.value.trim()) { errorMsg.value = '请填写收货人'; return }
  if (!receiverPhone.value.trim()) { errorMsg.value = '请填写联系电话'; return }
  if (!receiverAddress.value.trim()) { errorMsg.value = '请填写收货地址'; return }

  submitting.value = true
  try {
    const baseURL = config.public.apiBase
    const headers = { Authorization: `Bearer ${authStore.token}` }

    let result: { code: number, message: string, data: { id: number } }

    if (isDirectBuy.value) {
      const item = orderItems.value[0]
      result = await $fetch(`${baseURL}/api/orders/direct`, {
        method: 'POST',
        headers,
        body: {
          productId: item.productId,
          quantity: item.quantity,
          receiverName: receiverName.value,
          receiverPhone: receiverPhone.value,
          receiverAddress: receiverAddress.value,
          remark: remark.value,
        },
      })
    }
    else {
      result = await $fetch(`${baseURL}/api/orders`, {
        method: 'POST',
        headers,
        body: {
          cartItemIds: cartItemIds.value,
          receiverName: receiverName.value,
          receiverPhone: receiverPhone.value,
          receiverAddress: receiverAddress.value,
          remark: remark.value,
        },
      })
    }

    if (result.code === 200 && result.data) {
      await cartStore.fetchCart()
      router.push(`/orders/${result.data.id}`)
    }
    else {
      errorMsg.value = result.message || '下单失败'
    }
  }
  catch (e: unknown) {
    errorMsg.value = (e as Error).message || '下单失败，请重试'
  }
  finally {
    submitting.value = false
  }
}
</script>
<!-- AI-GENERATED-END -->
