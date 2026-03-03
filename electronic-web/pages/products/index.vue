<template>
  <div class="max-w-7xl mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-8">全部商品</h1>

    <div v-if="products.length" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
      <!-- AI-GENERATED-BEGIN -->
      <!-- GENERATED-BY-Claude -->
      <!-- @author Cursor Agent and ZKT AI 编程助手，2026-03-03 商品卡片增加详情页链接 -->
      <NuxtLink
        v-for="product in products"
        :key="product.id"
        :to="`/products/${product.id}`"
        class="bg-white rounded-xl shadow-md overflow-hidden hover:shadow-xl transition group"
      >
        <div class="aspect-video bg-gray-100 flex items-center justify-center overflow-hidden">
          <img
            :src="product.imageUrl"
            :alt="product.name"
            class="object-cover w-full h-full group-hover:scale-105 transition"
          >
        </div>
        <div class="p-4">
          <span class="text-xs bg-gray-100 text-gray-600 px-2 py-1 rounded">{{ product.category }}</span>
          <h3 class="font-semibold text-lg mt-2 mb-1">{{ product.name }}</h3>
          <p class="text-gray-500 text-sm mb-3">{{ product.description }}</p>
          <div class="flex items-center justify-between">
            <span class="text-primary text-xl font-bold">¥{{ product.price }}</span>
            <span class="bg-primary text-white px-4 py-2 rounded-lg group-hover:opacity-90 transition text-sm">
              查看详情
            </span>
          </div>
        </div>
      </NuxtLink>
      <!-- AI-GENERATED-END -->
    </div>

    <div v-else class="text-center py-20 text-gray-400">
      <p>加载中...</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Product } from '~/composables/useApi'

const { fetchProducts } = useApi()
const products = ref<Product[]>([])

onMounted(async () => {
  try {
    products.value = await fetchProducts()
  } catch {
    console.warn('API 未连接')
  }
})
</script>
