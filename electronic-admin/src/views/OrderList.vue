<!--
  文件名称：OrderList.vue
  描述：订单管理页面，支持查看和修改订单状态

  @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
  @since 2026-03-03
  @see src/api/order.ts
-->

<!-- AI-GENERATED-BEGIN -->
<!-- GENERATED-BY-Claude -->
<template>
  <div>
    <a-button type="primary" :loading="loading" style="margin-bottom: 16px" @click="loadOrders">
      刷新
    </a-button>

    <a-table
      :columns="columns"
      :data-source="orders"
      :loading="loading"
      row-key="id"
      :pagination="{ pageSize: 10 }"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'status'">
          <a-tag :color="statusColor(record.status)">
            {{ statusText(record.status) }}
          </a-tag>
        </template>
        <template v-if="column.key === 'totalAmount'">
          ¥{{ record.totalAmount.toFixed(2) }}
        </template>
        <template v-if="column.key === 'items'">
          {{ record.items.map((i: OrderItemData) => i.productName).join('、') }}
        </template>
        <template v-if="column.key === 'createdAt'">
          {{ formatTime(record.createdAt) }}
        </template>
        <template v-if="column.key === 'action'">
          <a-button size="small" @click="showDetail(record)">详情</a-button>
          <a-divider type="vertical" />
          <a-dropdown>
            <a-button size="small">修改状态</a-button>
            <template #overlay>
              <a-menu @click="(e: MenuClickEvent) => changeStatus(record.id, e.key as string)">
                <a-menu-item v-for="s in allStatuses" :key="s.value" :disabled="record.status === s.value">
                  {{ s.label }}
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </template>
      </template>
    </a-table>

    <a-modal v-model:open="detailVisible" title="订单详情" :footer="null" width="680px">
      <template v-if="currentOrder">
        <a-row :gutter="16" style="margin-bottom: 16px">
          <a-col :span="12"><strong>订单号：</strong>{{ currentOrder.orderNo }}</a-col>
          <a-col :span="12"><strong>状态：</strong>{{ statusText(currentOrder.status) }}</a-col>
        </a-row>
        <a-row :gutter="16" style="margin-bottom: 16px">
          <a-col :span="12"><strong>收货人：</strong>{{ currentOrder.receiverName }}</a-col>
          <a-col :span="12"><strong>电话：</strong>{{ currentOrder.receiverPhone }}</a-col>
        </a-row>
        <a-row :gutter="16" style="margin-bottom: 16px">
          <a-col :span="24"><strong>地址：</strong>{{ currentOrder.receiverAddress }}</a-col>
        </a-row>
        <a-row v-if="currentOrder.remark" :gutter="16" style="margin-bottom: 16px">
          <a-col :span="24"><strong>备注：</strong>{{ currentOrder.remark }}</a-col>
        </a-row>
        <a-divider>商品列表</a-divider>
        <a-table
          :columns="itemColumns"
          :data-source="currentOrder.items"
          row-key="id"
          :pagination="false"
          size="small"
        >
          <template #bodyCell="{ column, record: item }">
            <template v-if="column.key === 'productPrice'">
              ¥{{ item.productPrice.toFixed(2) }}
            </template>
            <template v-if="column.key === 'subtotal'">
              ¥{{ item.subtotal.toFixed(2) }}
            </template>
          </template>
        </a-table>
        <div style="text-align: right; margin-top: 16px; font-size: 16px">
          <strong>总额：¥{{ currentOrder.totalAmount.toFixed(2) }}</strong>
        </div>
      </template>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { getAllOrders, updateOrderStatus, type Order, type OrderItem as OrderItemData } from '../api/order'

interface MenuClickEvent {
  key: string
}

const orders = ref<Order[]>([])
const loading = ref(false)
const detailVisible = ref(false)
const currentOrder = ref<Order | null>(null)

const allStatuses = [
  { value: 'PENDING', label: '待付款' },
  { value: 'PAID', label: '已付款' },
  { value: 'SHIPPED', label: '已发货' },
  { value: 'COMPLETED', label: '已完成' },
  { value: 'CANCELLED', label: '已取消' },
]

const columns = [
  { title: '订单号', dataIndex: 'orderNo', key: 'orderNo', width: 180 },
  { title: '用户ID', dataIndex: 'userId', key: 'userId', width: 80 },
  { title: '商品', key: 'items', ellipsis: true },
  { title: '金额', key: 'totalAmount', width: 100 },
  { title: '状态', key: 'status', width: 100 },
  { title: '下单时间', key: 'createdAt', width: 170 },
  { title: '操作', key: 'action', width: 200 },
]

const itemColumns = [
  { title: '商品名称', dataIndex: 'productName', key: 'productName' },
  { title: '单价', key: 'productPrice', width: 100 },
  { title: '数量', dataIndex: 'quantity', key: 'quantity', width: 80 },
  { title: '小计', key: 'subtotal', width: 100 },
]

function statusText(status: string) {
  return allStatuses.find(s => s.value === status)?.label || status
}

function statusColor(status: string) {
  const map: Record<string, string> = {
    PENDING: 'orange', PAID: 'blue', SHIPPED: 'purple',
    COMPLETED: 'green', CANCELLED: 'default',
  }
  return map[status] || 'default'
}

function formatTime(dt: string) {
  if (!dt) return ''
  return dt.replace('T', ' ').substring(0, 19)
}

function showDetail(order: Order) {
  currentOrder.value = order
  detailVisible.value = true
}

async function loadOrders() {
  loading.value = true
  try {
    orders.value = await getAllOrders()
  } catch (e: unknown) {
    message.error('加载订单列表失败')
  } finally {
    loading.value = false
  }
}

async function changeStatus(orderId: number, status: string) {
  try {
    await updateOrderStatus(orderId, status)
    message.success('状态已更新')
    await loadOrders()
  } catch (e: unknown) {
    message.error('更新失败')
  }
}

onMounted(() => {
  loadOrders()
})
</script>
<!-- AI-GENERATED-END -->
