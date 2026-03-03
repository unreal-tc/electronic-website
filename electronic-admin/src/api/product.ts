/**
 * 文件名称：product.ts
 * 描述：商品管理 API 接口
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see src/views/ProductList.vue
 */

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
import axios from 'axios'

const http = axios.create({
  baseURL: '/api',
  timeout: 10000,
})

export interface Product {
  id?: number
  name: string
  description: string
  price: number
  stock: number
  imageUrl: string
  category: string
  enabled: boolean
  createdAt?: string
  updatedAt?: string
}

interface ApiResult<T> {
  code: number
  message: string
  data: T
}

export async function getProducts(): Promise<Product[]> {
  const res = await http.get<ApiResult<Product[]>>('/products')
  return res.data.data
}

export async function getProduct(id: number): Promise<Product> {
  const res = await http.get<ApiResult<Product>>(`/products/${id}`)
  return res.data.data
}

export async function createProduct(product: Product): Promise<Product> {
  const res = await http.post<ApiResult<Product>>('/products', product)
  return res.data.data
}

export async function updateProduct(id: number, product: Product): Promise<Product> {
  const res = await http.put<ApiResult<Product>>(`/products/${id}`, product)
  return res.data.data
}

export async function deleteProduct(id: number): Promise<void> {
  await http.delete(`/products/${id}`)
}
// AI-GENERATED-END
