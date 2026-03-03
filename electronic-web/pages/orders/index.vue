<!--
  文件名称：index.vue
  描述：用户订单列表页面

  @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
  @since 2026-03-03
  @see stores/auth.ts
-->

<!-- AI-GENERATED-BEGIN -->
<!-- GENERATED-BY-Claude -->
<template>
  <div class="max-w-5xl mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold mb-6">我的订单</h1>

    <div v-if="!authStore.isLoggedIn" class="text-center py-20">
      <p class="text-5xl mb-4">🔒</p>
      <p class="text-gray-500 mb-4">请先登录后查看订单</p>
      <NuxtLink
        to="/login?redirect=/orders"
        class="inline-block bg-primary text-white px-6 py-3 rounded-lg hover:opacity-90 transition"
      >
        去登录
      </NuxtLink>
    </div>

    <div v-else-if="loading" class="text-center py-20 text-gray-400">
      <div class="inline-block w-8 h-8 border-4 border-primary border-t-transparent rounded-full animate-spin mb-4" />
      <p>加载中...</p>
    </div>

    <div v-else-if="orders.length === 0" class="text-center py-20">
      <p class="text-5xl mb-4">📋</p>
      <h2 class="text-xl font-bold text-gray-700 mb-2">还没有订单</h2>
      <p class="text-gray-500 mb-6">快去选购喜欢的商品吧</p>
      <NuxtLink
        to="/products"
        class="inline-block bg-primary text-white px-6 py-3 rounded-lg hover:opacity-90 transition"
      >
        去选购
      </NuxtLink>
    </div>

    <div v-else class="space-y-4">
      <NuxtLink
        v-for="order in orders"
        :key="order.id"
        :to="`/orders/${order.id}`"
        class="block bg-white rounded-xl shadow-sm border hover:shadow-md transition p-5"
      >
        <div class="flex items-center justify-between mb-3">
          <span class="text-sm text-gray-500">订单号：{{ order.orderNo }}</span>
          <span :class="statusClass(order.status)" class="text-sm font-medium px-3 py-1 rounded-full">
            {{ statusText(order.status) }}
          </span>
        </div>
        <div class="flex items-center gap-3">
          <div class="flex -space-x-2">
            <img
              v-for="item in order.items.slice(0, 4)"
              :key="item.id"
              :src="item.productImageUrl"
              :alt="item.productName"
              class="w-14 h-14 object-cover rounded-lg border-2 border-white"
            >
          </div>
          <div class="flex-1 min-w-0">
            <p class="text-gray-800 font-medium truncate">
              {{ order.items.map((i: OrderItemData) => i.productName).join('、') }}
            </p>
            <p class="text-sm text-gray-500 mt-1">
              共 {{ order.items.reduce((s: number, i: OrderItemData) => s + i.quantity, 0) }} 件商品
            </p>
          </div>
          <div class="text-right flex-shrink-0">
            <p class="text-primary text-lg font-bold">¥{{ order.totalAmount.toFixed(2) }}</p>
            <p class="text-xs text-gray-400 mt-1">{{ formatTime(order.createdAt) }}</p>
          </div>
        </div>
      </NuxtLink>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '~/stores/auth'

interface OrderItemData {
  id: number
  productId: number
  productName: string
  productImageUrl: string
  productPrice: number
  quantity: number
  subtotal: number
}

interface OrderData {
  id: number
  orderNo: string
  totalAmount: number
  status: string
  items: OrderItemData[]
  createdAt: string
}

const authStore = useAuthStore()
const config = useRuntimeConfig()
const orders = ref<OrderData[]>([])
const loading = ref(true)

function statusText(status: string) {
  const map: Record<string, string> = {
    PENDING: '待付款', PAID: '已付款', SHIPPED: '已发货',
    COMPLETED: '已完成', CANCELLED: '已取消',
  }
  return map[status] || status
}

function statusClass(status: string) {
  const map: Record<string, string> = {
    PENDING: 'bg-yellow-100 text-yellow-700',
    PAID: 'bg-blue-100 text-blue-700',
    SHIPPED: 'bg-purple-100 text-purple-700',
    COMPLETED: 'bg-green-100 text-green-700',
    CANCELLED: 'bg-gray-100 text-gray-500',
  }
  return map[status] || 'bg-gray-100 text-gray-500'
}

function formatTime(dt: string) {
  if (!dt) return ''
  return dt.replace('T', ' ').substring(0, 16)
}

onMounted(async () => {
  if (!authStore.isLoggedIn) { loading.value = false; return }
  try {
    const result = await $fetch<{ code: number, data: OrderData[] }>(
      `${config.public.apiBase}/api/orders`,
      { headers: { Authorization: `Bearer ${authStore.token}` } },
    )
    orders.value = result.data ?? []
  }
  catch {
    console.warn('获取订单失败')
  }
  finally {
    loading.value = false
  }
})
</script>
<!-- AI-GENERATED-END -->
