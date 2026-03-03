<!--
  文件名称：[id].vue
  描述：商品详情页，展示商品完整信息、加入购物车与立即购买

  @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
  @since 2026-03-03
  @see composables/useApi.ts
-->

<!-- AI-GENERATED-BEGIN -->
<!-- GENERATED-BY-Claude -->
<template>
  <div class="max-w-7xl mx-auto px-4 py-8">
    <nav class="flex items-center gap-2 text-sm text-gray-500 mb-8">
      <NuxtLink to="/" class="hover:text-primary transition">首页</NuxtLink>
      <span>/</span>
      <NuxtLink to="/products" class="hover:text-primary transition">全部商品</NuxtLink>
      <span>/</span>
      <span class="text-gray-800">{{ product?.name ?? '商品详情' }}</span>
    </nav>

    <div v-if="loading" class="text-center py-32 text-gray-400">
      <div class="inline-block w-8 h-8 border-4 border-primary border-t-transparent rounded-full animate-spin mb-4" />
      <p>加载中...</p>
    </div>

    <div v-else-if="!product" class="text-center py-32">
      <p class="text-6xl mb-4">😕</p>
      <h2 class="text-2xl font-bold text-gray-700 mb-2">商品不存在</h2>
      <p class="text-gray-500 mb-6">该商品可能已下架或链接有误</p>
      <NuxtLink
        to="/products"
        class="inline-block bg-primary text-white px-6 py-3 rounded-lg hover:opacity-90 transition"
      >
        返回商品列表
      </NuxtLink>
    </div>

    <div v-else>
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-10">
        <div class="bg-white rounded-2xl shadow-md overflow-hidden">
          <div class="aspect-square bg-gray-50 flex items-center justify-center">
            <img
              :src="product.imageUrl"
              :alt="product.name"
              class="object-cover w-full h-full"
            >
          </div>
        </div>

        <div class="flex flex-col">
          <span class="inline-block self-start text-xs bg-gray-100 text-gray-600 px-3 py-1 rounded-full mb-3">
            {{ product.category }}
          </span>

          <h1 class="text-3xl font-bold text-gray-900 mb-4">{{ product.name }}</h1>

          <div class="bg-orange-50 rounded-xl px-6 py-4 mb-6">
            <span class="text-sm text-gray-500">价格</span>
            <div class="text-primary text-4xl font-bold mt-1">
              ¥{{ product.price.toFixed(2) }}
            </div>
          </div>

          <div class="text-gray-600 leading-relaxed mb-6">
            <h3 class="text-sm font-semibold text-gray-800 mb-2">商品描述</h3>
            <p>{{ product.description }}</p>
          </div>

          <div class="flex items-center gap-4 mb-6">
            <span class="text-sm text-gray-500">库存</span>
            <span
              :class="product.stock > 0 ? 'text-green-600' : 'text-red-500'"
              class="font-semibold"
            >
              {{ product.stock > 0 ? `有货（${product.stock} 件）` : '暂时缺货' }}
            </span>
          </div>

          <div class="flex items-center gap-4 mb-8">
            <span class="text-sm text-gray-500">数量</span>
            <div class="flex items-center border rounded-lg overflow-hidden">
              <button
                class="w-10 h-10 flex items-center justify-center text-lg hover:bg-gray-100 transition disabled:opacity-40 disabled:cursor-not-allowed"
                :disabled="quantity <= 1"
                @click="quantity--"
              >
                −
              </button>
              <span class="w-14 h-10 flex items-center justify-center border-x font-semibold">
                {{ quantity }}
              </span>
              <button
                class="w-10 h-10 flex items-center justify-center text-lg hover:bg-gray-100 transition disabled:opacity-40 disabled:cursor-not-allowed"
                :disabled="quantity >= product.stock"
                @click="quantity++"
              >
                +
              </button>
            </div>
          </div>

          <p v-if="actionMsg" :class="actionMsgType === 'success' ? 'text-green-600' : 'text-red-500'" class="text-sm mb-4">
            {{ actionMsg }}
          </p>

          <div class="flex gap-4 mt-auto">
            <button
              class="flex-1 bg-primary text-white py-3 rounded-xl text-lg font-semibold hover:opacity-90 transition disabled:opacity-50 disabled:cursor-not-allowed"
              :disabled="product.stock <= 0 || addingToCart"
              @click="handleAddToCart"
            >
              {{ addingToCart ? '添加中...' : '加入购物车' }}
            </button>
            <button
              class="flex-1 bg-orange-600 text-white py-3 rounded-xl text-lg font-semibold hover:opacity-90 transition disabled:opacity-50 disabled:cursor-not-allowed"
              :disabled="product.stock <= 0"
              @click="handleBuyNow"
            >
              立即购买
            </button>
          </div>
        </div>
      </div>

      <section v-if="relatedProducts.length" class="mt-16">
        <h2 class="text-2xl font-bold mb-6">相关推荐</h2>
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
          <NuxtLink
            v-for="item in relatedProducts"
            :key="item.id"
            :to="`/products/${item.id}`"
            class="bg-white rounded-xl shadow-md overflow-hidden hover:shadow-xl transition group"
          >
            <div class="aspect-square bg-gray-100 flex items-center justify-center overflow-hidden">
              <img
                :src="item.imageUrl"
                :alt="item.name"
                class="object-cover w-full h-full group-hover:scale-105 transition"
              >
            </div>
            <div class="p-4">
              <h3 class="font-semibold text-lg mb-1 truncate">{{ item.name }}</h3>
              <span class="text-primary text-lg font-bold">¥{{ item.price }}</span>
            </div>
          </NuxtLink>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Product } from '~/composables/useApi'
import { useAuthStore } from '~/stores/auth'
import { useCartStore } from '~/stores/cart'

const route = useRoute()
const router = useRouter()
const { fetchProduct, fetchProductsByCategory } = useApi()
const authStore = useAuthStore()
const cartStore = useCartStore()

const product = ref<Product | null>(null)
const relatedProducts = ref<Product[]>([])
const loading = ref(true)
const quantity = ref(1)
const addingToCart = ref(false)
const actionMsg = ref('')
const actionMsgType = ref<'success' | 'error'>('success')

const productId = computed(() => Number(route.params.id))

onMounted(async () => {
  await loadProduct()
})

watch(() => route.params.id, async () => {
  quantity.value = 1
  actionMsg.value = ''
  await loadProduct()
})

async function loadProduct() {
  loading.value = true
  try {
    product.value = await fetchProduct(productId.value)
    if (product.value) {
      const categoryProducts = await fetchProductsByCategory(product.value.category)
      relatedProducts.value = categoryProducts.filter(p => p.id !== product.value!.id).slice(0, 4)
    }
  }
  catch {
    console.warn('API 未连接')
  }
  finally {
    loading.value = false
  }
}

async function handleAddToCart() {
  if (!authStore.isLoggedIn) {
    router.push(`/login?redirect=/products/${productId.value}`)
    return
  }
  addingToCart.value = true
  actionMsg.value = ''
  try {
    await cartStore.addToCart(productId.value, quantity.value)
    actionMsg.value = '已成功加入购物车！'
    actionMsgType.value = 'success'
  }
  catch (e: unknown) {
    actionMsg.value = (e as Error).message || '添加失败'
    actionMsgType.value = 'error'
  }
  finally {
    addingToCart.value = false
  }
}

function handleBuyNow() {
  if (!authStore.isLoggedIn) {
    router.push(`/login?redirect=/products/${productId.value}`)
    return
  }
  router.push(`/checkout?productId=${productId.value}&quantity=${quantity.value}`)
}
</script>
<!-- AI-GENERATED-END -->
