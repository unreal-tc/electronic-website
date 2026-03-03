/**
 * 文件名称：order.ts
 * 描述：订单管理 API 接口
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see src/views/OrderList.vue
 */

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
import axios from 'axios'

const http = axios.create({
  baseURL: '/api',
  timeout: 10000,
})

export interface OrderItem {
  id: number
  productId: number
  productName: string
  productImageUrl: string
  productPrice: number
  quantity: number
  subtotal: number
}

export interface Order {
  id: number
  orderNo: string
  userId: number
  totalAmount: number
  status: string
  receiverName: string
  receiverPhone: string
  receiverAddress: string
  remark: string
  items: OrderItem[]
  createdAt: string
  updatedAt: string
}

interface ApiResult<T> {
  code: number
  message: string
  data: T
}

export async function getAllOrders(): Promise<Order[]> {
  const res = await http.get<ApiResult<Order[]>>('/orders/all')
  return res.data.data
}

export async function updateOrderStatus(id: number, status: string): Promise<Order> {
  const res = await http.put<ApiResult<Order>>(`/orders/${id}/status`, { status })
  return res.data.data
}
// AI-GENERATED-END
