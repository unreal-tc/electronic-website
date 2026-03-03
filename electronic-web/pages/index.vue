<template>
  <div>
    <!-- Hero -->
    <section class="bg-gradient-to-r from-orange-500 to-red-500 text-white py-20">
      <div class="max-w-7xl mx-auto px-4 text-center">
        <h1 class="text-5xl font-bold mb-4">优质好物，尽在这里</h1>
        <p class="text-xl mb-8 opacity-90">发现属于你的品质生活</p>
        <NuxtLink
          to="/products"
          class="inline-block bg-white text-primary font-semibold px-8 py-3 rounded-full hover:shadow-lg transition"
        >
          立即选购
        </NuxtLink>
      </div>
    </section>

    <!-- Products -->
    <section class="max-w-7xl mx-auto px-4 py-12">
      <h2 class="text-3xl font-bold text-center mb-10">热门商品</h2>

      <div v-if="products.length" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
        <!-- AI-GENERATED-BEGIN -->
        <!-- GENERATED-BY-Claude -->
        <!-- @author Cursor Agent and ZKT AI 编程助手，2026-03-03 商品卡片增加详情页链接 -->
        <NuxtLink
          v-for="product in products"
          :key="product.id"
          :to="`/products/${product.id}`"
          class="bg-white rounded-xl shadow-md overflow-hidden hover:shadow-xl transition group"
        >
          <div class="aspect-square bg-gray-100 flex items-center justify-center overflow-hidden">
            <img
              :src="product.imageUrl"
              :alt="product.name"
              class="object-cover w-full h-full group-hover:scale-105 transition"
            >
          </div>
          <div class="p-4">
            <h3 class="font-semibold text-lg mb-1 truncate">{{ product.name }}</h3>
            <p class="text-gray-500 text-sm mb-2 line-clamp-2">{{ product.description }}</p>
            <div class="flex items-center justify-between">
              <span class="text-primary text-xl font-bold">¥{{ product.price }}</span>
              <span class="text-gray-400 text-sm">库存 {{ product.stock }}</span>
            </div>
          </div>
        </NuxtLink>
        <!-- AI-GENERATED-END -->
      </div>

      <div v-else class="text-center py-20 text-gray-400">
        <p class="text-lg">暂无商品数据</p>
        <p class="text-sm mt-2">请确保 API 服务已启动 (localhost:8080)</p>
      </div>
    </section>
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
    console.warn('API 未连接，显示空状态')
  }
})
</script>
