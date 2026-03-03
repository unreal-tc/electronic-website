<template>
  <div>
    <a-row :gutter="16">
      <a-col :span="4">
        <a-statistic title="商品总数" :value="stats.totalProducts" class="stat-card">
          <template #prefix>
            <ShoppingOutlined />
          </template>
        </a-statistic>
      </a-col>
      <a-col :span="4">
        <a-statistic title="上架商品" :value="stats.enabledProducts" class="stat-card">
          <template #prefix>
            <CheckCircleOutlined style="color: #52c41a" />
          </template>
        </a-statistic>
      </a-col>
      <a-col :span="4">
        <a-statistic title="商品分类" :value="stats.categories" class="stat-card">
          <template #prefix>
            <AppstoreOutlined />
          </template>
        </a-statistic>
      </a-col>
      <a-col :span="4">
        <a-statistic title="库存总量" :value="stats.totalStock" class="stat-card">
          <template #prefix>
            <DatabaseOutlined />
          </template>
        </a-statistic>
      </a-col>
      <!-- AI-GENERATED-BEGIN -->
      <!-- GENERATED-BY-Claude -->
      <!-- @author Cursor Agent and ZKT AI 编程助手，2026-03-03 新增用户和订单统计 -->
      <a-col :span="4">
        <a-statistic title="注册用户" :value="stats.totalUsers" class="stat-card">
          <template #prefix>
            <UserOutlined style="color: #1890ff" />
          </template>
        </a-statistic>
      </a-col>
      <a-col :span="4">
        <a-statistic title="订单总数" :value="stats.totalOrders" class="stat-card">
          <template #prefix>
            <OrderedListOutlined style="color: #722ed1" />
          </template>
        </a-statistic>
      </a-col>
      <!-- AI-GENERATED-END -->
    </a-row>

    <a-divider />

    <a-card title="最近商品" :bordered="false">
      <a-table
        :data-source="products"
        :columns="columns"
        :pagination="false"
        :loading="loading"
        size="small"
      />
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import {
  ShoppingOutlined,
  CheckCircleOutlined,
  AppstoreOutlined,
  DatabaseOutlined,
  // @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude，2026-03-03 新增图标导入
  UserOutlined,
  OrderedListOutlined,
} from '@ant-design/icons-vue'
import { getProducts, type Product } from '../api/product'
// @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude，2026-03-03 新增用户和订单API导入
import { getUsers } from '../api/user'
import { getAllOrders } from '../api/order'

const products = ref<Product[]>([])
const loading = ref(false)

const stats = reactive({
  totalProducts: 0,
  enabledProducts: 0,
  categories: 0,
  totalStock: 0,
  // @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude，2026-03-03 新增用户和订单统计字段
  totalUsers: 0,
  totalOrders: 0,
})

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id' },
  { title: '商品名称', dataIndex: 'name', key: 'name' },
  { title: '分类', dataIndex: 'category', key: 'category' },
  { title: '价格', dataIndex: 'price', key: 'price' },
  { title: '库存', dataIndex: 'stock', key: 'stock' },
]

onMounted(async () => {
  loading.value = true
  try {
    products.value = await getProducts()
    stats.totalProducts = products.value.length
    stats.enabledProducts = products.value.filter((p) => p.enabled).length
    stats.categories = new Set(products.value.map((p) => p.category)).size
    stats.totalStock = products.value.reduce((sum, p) => sum + p.stock, 0)

    // @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude，2026-03-03 加载用户和订单统计
    const [users, orders] = await Promise.all([getUsers(), getAllOrders()])
    stats.totalUsers = users.length
    stats.totalOrders = orders.length
  } catch {
    console.warn('API 未连接')
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.stat-card {
  padding: 16px;
  background: #fafafa;
  border-radius: 8px;
}
</style>
