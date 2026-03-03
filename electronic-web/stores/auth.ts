/**
 * 文件名称：auth.ts
 * 描述：用户认证状态管理，支持手机号注册登录
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see composables/useApi.ts
 */

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
import { defineStore } from 'pinia'

export interface UserInfo {
  id: number
  phone: string
  nickname: string
  avatar: string | null
  token: string
  enabled: boolean
  createdAt: string
}

export const useAuthStore = defineStore('auth', () => {
  const user = ref<UserInfo | null>(null)
  const config = useRuntimeConfig()
  const baseURL = config.public.apiBase

  const isLoggedIn = computed(() => !!user.value)
  const token = computed(() => user.value?.token ?? '')

  function loadFromStorage() {
    if (import.meta.client) {
      const stored = localStorage.getItem('user')
      if (stored) {
        try {
          user.value = JSON.parse(stored)
        }
        catch {
          localStorage.removeItem('user')
        }
      }
    }
  }

  function saveToStorage() {
    if (import.meta.client) {
      if (user.value) {
        localStorage.setItem('user', JSON.stringify(user.value))
      }
      else {
        localStorage.removeItem('user')
      }
    }
  }

  async function register(phone: string, password: string, nickname?: string) {
    const result = await $fetch<{ code: number, message: string, data: UserInfo }>(`${baseURL}/api/users/register`, {
      method: 'POST',
      body: { phone, password, nickname },
    })
    if (result.code !== 200) {
      throw new Error(result.message)
    }
    user.value = result.data
    saveToStorage()
    return result.data
  }

  async function login(phone: string, password: string) {
    const result = await $fetch<{ code: number, message: string, data: UserInfo }>(`${baseURL}/api/users/login`, {
      method: 'POST',
      body: { phone, password },
    })
    if (result.code !== 200) {
      throw new Error(result.message)
    }
    user.value = result.data
    saveToStorage()
    return result.data
  }

  function logout() {
    user.value = null
    saveToStorage()
  }

  async function fetchUserInfo() {
    if (!token.value) return
    try {
      const result = await $fetch<{ code: number, message: string, data: UserInfo }>(`${baseURL}/api/users/info`, {
        headers: { Authorization: `Bearer ${token.value}` },
      })
      if (result.code === 200) {
        user.value = result.data
        saveToStorage()
      }
      else {
        logout()
      }
    }
    catch {
      logout()
    }
  }

  loadFromStorage()

  return { user, isLoggedIn, token, register, login, logout, fetchUserInfo }
})
// AI-GENERATED-END
