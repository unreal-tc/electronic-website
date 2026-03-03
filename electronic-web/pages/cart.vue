<!--
  文件名称：cart.vue
  描述：购物车页面，展示购物车列表和结算功能

  @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
  @since 2026-03-03
  @see stores/cart.ts
-->

<!-- AI-GENERATED-BEGIN -->
<!-- GENERATED-BY-Claude -->
<template>
  <div class="max-w-5xl mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold mb-6">我的购物车</h1>

    <div v-if="!authStore.isLoggedIn" class="text-center py-20">
      <p class="text-5xl mb-4">🔒</p>
      <p class="text-gray-500 mb-4">请先登录后查看购物车</p>
      <NuxtLink
        to="/login?redirect=/cart"
        class="inline-block bg-primary text-white px-6 py-3 rounded-lg hover:opacity-90 transition"
      >
        去登录
      </NuxtLink>
    </div>

    <div v-else-if="cartStore.loading" class="text-center py-20 text-gray-400">
      <div class="inline-block w-8 h-8 border-4 border-primary border-t-transparent rounded-full animate-spin mb-4" />
      <p>加载中...</p>
    </div>

    <div v-else-if="cartStore.items.length === 0" class="text-center py-20">
      <p class="text-5xl mb-4">🛒</p>
      <h2 class="text-xl font-bold text-gray-700 mb-2">购物车是空的</h2>
      <p class="text-gray-500 mb-6">快去挑选心仪的商品吧</p>
      <NuxtLink
        to="/products"
        class="inline-block bg-primary text-white px-6 py-3 rounded-lg hover:opacity-90 transition"
      >
        去逛逛
      </NuxtLink>
    </div>

    <div v-else>
      <div class="space-y-4">
        <div
          v-for="item in cartStore.items"
          :key="item.id"
          class="bg-white rounded-xl shadow-sm border p-4 flex items-center gap-4"
        >
          <input
            v-model="selectedIds"
            type="checkbox"
            :value="item.id"
            class="w-5 h-5 accent-primary flex-shrink-0"
          >
          <NuxtLink :to="`/products/${item.product.id}`" class="flex-shrink-0">
            <img
              :src="item.product.imageUrl"
              :alt="item.product.name"
              class="w-20 h-20 object-cover rounded-lg"
            >
          </NuxtLink>
          <div class="flex-1 min-w-0">
            <NuxtLink :to="`/products/${item.product.id}`" class="font-semibold text-gray-800 hover:text-primary transition truncate block">
              {{ item.product.name }}
            </NuxtLink>
            <p class="text-primary font-bold mt-1">¥{{ item.product.price.toFixed(2) }}</p>
          </div>
          <div class="flex items-center border rounded-lg overflow-hidden flex-shrink-0">
            <button
              class="w-8 h-8 flex items-center justify-center hover:bg-gray-100 transition disabled:opacity-40"
              :disabled="item.quantity <= 1"
              @click="changeQuantity(item.id, item.quantity - 1)"
            >
              −
            </button>
            <span class="w-10 h-8 flex items-center justify-center border-x text-sm font-semibold">
              {{ item.quantity }}
            </span>
            <button
              class="w-8 h-8 flex items-center justify-center hover:bg-gray-100 transition disabled:opacity-40"
              :disabled="item.quantity >= item.product.stock"
              @click="changeQuantity(item.id, item.quantity + 1)"
            >
              +
            </button>
          </div>
          <p class="text-primary font-bold w-24 text-right flex-shrink-0">
            ¥{{ (item.product.price * item.quantity).toFixed(2) }}
          </p>
          <button
            class="text-gray-400 hover:text-red-500 transition flex-shrink-0"
            @click="removeItem(item.id)"
          >
            ✕
          </button>
        </div>
      </div>

      <div class="mt-8 bg-white rounded-xl shadow-sm border p-6 flex items-center justify-between">
        <label class="flex items-center gap-2 cursor-pointer">
          <input
            type="checkbox"
            :checked="isAllSelected"
            class="w-5 h-5 accent-primary"
            @change="toggleSelectAll"
          >
          <span class="text-gray-600">全选</span>
        </label>
        <div class="flex items-center gap-6">
          <p class="text-gray-600">
            已选 <span class="text-primary font-bold">{{ selectedIds.length }}</span> 件，
            合计：<span class="text-primary text-xl font-bold">¥{{ selectedTotal.toFixed(2) }}</span>
          </p>
          <button
            class="bg-primary text-white px-8 py-3 rounded-lg font-semibold hover:opacity-90 transition disabled:opacity-50"
            :disabled="selectedIds.length === 0"
            @click="goCheckout"
          >
            结算
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '~/stores/auth'
import { useCartStore } from '~/stores/cart'

const authStore = useAuthStore()
const cartStore = useCartStore()
const router = useRouter()

const selectedIds = ref<number[]>([])

const isAllSelected = computed(() =>
  cartStore.items.length > 0 && selectedIds.value.length === cartStore.items.length,
)

const selectedTotal = computed(() => {
  return cartStore.items
    .filter(i => selectedIds.value.includes(i.id))
    .reduce((sum, i) => sum + i.product.price * i.quantity, 0)
})

function toggleSelectAll() {
  if (isAllSelected.value) {
    selectedIds.value = []
  }
  else {
    selectedIds.value = cartStore.items.map(i => i.id)
  }
}

async function changeQuantity(cartItemId: number, quantity: number) {
  try {
    await cartStore.updateQuantity(cartItemId, quantity)
  }
  catch (e: unknown) {
    alert((e as Error).message)
  }
}

async function removeItem(cartItemId: number) {
  await cartStore.removeItem(cartItemId)
  selectedIds.value = selectedIds.value.filter(id => id !== cartItemId)
}

function goCheckout() {
  const ids = selectedIds.value.join(',')
  router.push(`/checkout?cartItems=${ids}`)
}

onMounted(() => {
  if (authStore.isLoggedIn) {
    cartStore.fetchCart()
  }
})
</script>
<!-- AI-GENERATED-END -->
