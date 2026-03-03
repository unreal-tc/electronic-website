/**
 * 文件名称：user.ts
 * 描述：用户管理 API 接口
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see src/views/UserList.vue
 */

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
import axios from 'axios'

const http = axios.create({
  baseURL: '/api',
  timeout: 10000,
})

export interface User {
  id: number
  phone: string
  nickname: string
  avatar: string | null
  enabled: boolean
  createdAt: string
  updatedAt: string
}

interface ApiResult<T> {
  code: number
  message: string
  data: T
}

export async function getUsers(): Promise<User[]> {
  const res = await http.get<ApiResult<User[]>>('/users')
  return res.data.data
}

export async function updateUserStatus(id: number, enabled: boolean): Promise<User> {
  const res = await http.put<ApiResult<User>>(`/users/${id}/status`, { enabled })
  return res.data.data
}
// AI-GENERATED-END
