<template>
  <div>
    <div style="margin-bottom: 16px; display: flex; justify-content: space-between">
      <h3 style="margin: 0">商品列表</h3>
      <a-button type="primary" @click="showModal = true">
        <PlusOutlined /> 新增商品
      </a-button>
    </div>

    <a-table
      :data-source="products"
      :columns="columns"
      :loading="loading"
      row-key="id"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'price'">
          ¥{{ record.price }}
        </template>
        <template v-if="column.key === 'enabled'">
          <a-tag :color="record.enabled ? 'green' : 'red'">
            {{ record.enabled ? '上架' : '下架' }}
          </a-tag>
        </template>
        <template v-if="column.key === 'action'">
          <a-button type="link" size="small" @click="handleEdit(record)">编辑</a-button>
          <a-popconfirm title="确定要删除？" @confirm="handleDelete(record.id)">
            <a-button type="link" danger size="small">删除</a-button>
          </a-popconfirm>
        </template>
      </template>
    </a-table>

    <a-modal
      v-model:open="showModal"
      :title="editingId ? '编辑商品' : '新增商品'"
      @ok="handleSubmit"
      @cancel="resetForm"
    >
      <a-form :label-col="{ span: 5 }">
        <a-form-item label="商品名称">
          <a-input v-model:value="form.name" placeholder="请输入商品名称" />
        </a-form-item>
        <a-form-item label="描述">
          <a-textarea v-model:value="form.description" placeholder="请输入商品描述" />
        </a-form-item>
        <a-form-item label="价格">
          <a-input-number v-model:value="form.price" :min="0" :step="0.01" style="width: 100%" />
        </a-form-item>
        <a-form-item label="库存">
          <a-input-number v-model:value="form.stock" :min="0" style="width: 100%" />
        </a-form-item>
        <a-form-item label="分类">
          <a-input v-model:value="form.category" placeholder="请输入分类" />
        </a-form-item>
        <a-form-item label="图片URL">
          <a-input v-model:value="form.imageUrl" placeholder="请输入图片地址" />
        </a-form-item>
        <a-form-item label="上架">
          <a-switch v-model:checked="form.enabled" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import {
  getProducts,
  createProduct,
  updateProduct,
  deleteProduct,
  type Product,
} from '../api/product'

const products = ref<Product[]>([])
const loading = ref(false)
const showModal = ref(false)
const editingId = ref<number | null>(null)

const form = ref<Product>({
  name: '',
  description: '',
  price: 0,
  stock: 0,
  imageUrl: '',
  category: '',
  enabled: true,
})

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 60 },
  { title: '商品名称', dataIndex: 'name', key: 'name' },
  { title: '分类', dataIndex: 'category', key: 'category', width: 120 },
  { title: '价格', dataIndex: 'price', key: 'price', width: 120 },
  { title: '库存', dataIndex: 'stock', key: 'stock', width: 80 },
  { title: '状态', dataIndex: 'enabled', key: 'enabled', width: 80 },
  { title: '操作', key: 'action', width: 150 },
]

async function loadData() {
  loading.value = true
  try {
    products.value = await getProducts()
  } catch {
    message.error('加载商品失败，请确认 API 已启动')
  } finally {
    loading.value = false
  }
}

function handleEdit(record: Product) {
  editingId.value = record.id ?? null
  form.value = { ...record }
  showModal.value = true
}

async function handleDelete(id: number) {
  await deleteProduct(id)
  message.success('删除成功')
  await loadData()
}

async function handleSubmit() {
  try {
    if (editingId.value) {
      await updateProduct(editingId.value, form.value)
      message.success('更新成功')
    } else {
      await createProduct(form.value)
      message.success('创建成功')
    }
    resetForm()
    await loadData()
  } catch {
    message.error('操作失败')
  }
}

function resetForm() {
  showModal.value = false
  editingId.value = null
  form.value = {
    name: '',
    description: '',
    price: 0,
    stock: 0,
    imageUrl: '',
    category: '',
    enabled: true,
  }
}

onMounted(loadData)
</script>
