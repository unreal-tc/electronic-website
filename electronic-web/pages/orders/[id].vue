<!--
  文件名称：[id].vue
  描述：订单详情页面

  @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
  @since 2026-03-03
  @see stores/auth.ts
-->

<!-- AI-GENERATED-BEGIN -->
<!-- GENERATED-BY-Claude -->
<template>
  <div class="max-w-4xl mx-auto px-4 py-8">
    <nav class="flex items-center gap-2 text-sm text-gray-500 mb-6">
      <NuxtLink to="/" class="hover:text-primary transition">首页</NuxtLink>
      <span>/</span>
      <NuxtLink to="/orders" class="hover:text-primary transition">我的订单</NuxtLink>
      <span>/</span>
      <span class="text-gray-800">订单详情</span>
    </nav>

    <div v-if="loading" class="text-center py-20 text-gray-400">
      <div class="inline-block w-8 h-8 border-4 border-primary border-t-transparent rounded-full animate-spin mb-4" />
      <p>加载中...</p>
    </div>

    <div v-else-if="!order" class="text-center py-20">
      <p class="text-5xl mb-4">😕</p>
      <p class="text-gray-500">订单不存在</p>
    </div>

    <div v-else>
      <div class="bg-white rounded-xl shadow-sm border p-6 mb-6">
        <div class="flex items-center justify-between mb-4">
          <h1 class="text-xl font-bold">订单详情</h1>
          <span :class="statusClass(order.status)" class="text-sm font-medium px-4 py-1.5 rounded-full">
            {{ statusText(order.status) }}
          </span>
        </div>
        <div class="grid grid-cols-2 gap-4 text-sm text-gray-600">
          <p>订单编号：<span class="text-gray-800">{{ order.orderNo }}</span></p>
          <p>下单时间：<span class="text-gray-800">{{ formatTime(order.createdAt) }}</span></p>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-sm border p-6 mb-6">
        <h2 class="text-lg font-semibold mb-4">商品信息</h2>
        <div class="divide-y">
          <div v-for="item in order.items" :key="item.id" class="flex items-center gap-4 py-3">
            <NuxtLink :to="`/products/${item.productId}`">
              <img
                :src="item.productImageUrl"
                :alt="item.productName"
                class="w-20 h-20 object-cover rounded-lg"
              >
            </NuxtLink>
            <div class="flex-1">
              <NuxtLink :to="`/products/${item.productId}`" class="font-medium text-gray-800 hover:text-primary">
                {{ item.productName }}
              </NuxtLink>
              <p class="text-sm text-gray-500 mt-1">¥{{ item.productPrice.toFixed(2) }} × {{ item.quantity }}</p>
            </div>
            <p class="text-primary font-bold">¥{{ item.subtotal.toFixed(2) }}</p>
          </div>
        </div>
        <div class="flex justify-end pt-4 border-t mt-4">
          <p class="text-lg">
            订单总额：<span class="text-primary text-2xl font-bold">¥{{ order.totalAmount.toFixed(2) }}</span>
          </p>
        </div>
      </div>

      <div class="bg-white rounded-xl shadow-sm border p-6">
        <h2 class="text-lg font-semibold mb-4">收货信息</h2>
        <div class="space-y-2 text-sm text-gray-600">
          <p>收货人：<span class="text-gray-800">{{ order.receiverName }}</span></p>
          <p>联系电话：<span class="text-gray-800">{{ order.receiverPhone }}</span></p>
          <p>收货地址：<span class="text-gray-800">{{ order.receiverAddress }}</span></p>
          <p v-if="order.remark">备注：<span class="text-gray-800">{{ order.remark }}</span></p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '~/stores/auth'

interface OrderDetail {
  id: number
  orderNo: string
  totalAmount: number
  status: string
  receiverName: string
  receiverPhone: string
  receiverAddress: string
  remark: string
  items: {
    id: number
    productId: number
    productName: string
    productImageUrl: string
    productPrice: number
    quantity: number
    subtotal: number
  }[]
  createdAt: string
}

const authStore = useAuthStore()
const route = useRoute()
const config = useRuntimeConfig()
const order = ref<OrderDetail | null>(null)
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
    const orderId = Number(route.params.id)
    const result = await $fetch<{ code: number, data: OrderDetail }>(
      `${config.public.apiBase}/api/orders/${orderId}`,
      { headers: { Authorization: `Bearer ${authStore.token}` } },
    )
    order.value = result.data ?? null
  }
  catch {
    console.warn('获取订单详情失败')
  }
  finally {
    loading.value = false
  }
})
</script>
<!-- AI-GENERATED-END -->
