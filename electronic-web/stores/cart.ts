/**
 * 文件名称：cart.ts
 * 描述：购物车状态管理
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see composables/useApi.ts
 */

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
import { defineStore } from 'pinia'
import { useAuthStore } from './auth'

export interface CartItemData {
  id: number
  userId: number
  product: {
    id: number
    name: string
    price: number
    stock: number
    imageUrl: string
    category: string
  }
  quantity: number
  createdAt: string
}

export const useCartStore = defineStore('cart', () => {
  const items = ref<CartItemData[]>([])
  const loading = ref(false)
  const config = useRuntimeConfig()
  const baseURL = config.public.apiBase

  const totalCount = computed(() => items.value.reduce((sum, i) => sum + i.quantity, 0))
  const totalAmount = computed(() =>
    items.value.reduce((sum, i) => sum + i.product.price * i.quantity, 0),
  )

  function getHeaders() {
    const auth = useAuthStore()
    return { Authorization: `Bearer ${auth.token}` }
  }

  async function fetchCart() {
    const auth = useAuthStore()
    if (!auth.isLoggedIn) {
      items.value = []
      return
    }
    loading.value = true
    try {
      const result = await $fetch<{ code: number, data: CartItemData[] }>(`${baseURL}/api/cart`, {
        headers: getHeaders(),
      })
      items.value = result.data ?? []
    }
    catch {
      items.value = []
    }
    finally {
      loading.value = false
    }
  }

  async function addToCart(productId: number, quantity: number) {
    const result = await $fetch<{ code: number, message: string, data: CartItemData }>(`${baseURL}/api/cart`, {
      method: 'POST',
      headers: getHeaders(),
      body: { productId, quantity },
    })
    if (result.code !== 200) {
      throw new Error(result.message)
    }
    await fetchCart()
    return result.data
  }

  async function updateQuantity(cartItemId: number, quantity: number) {
    const result = await $fetch<{ code: number, message: string }>(`${baseURL}/api/cart/${cartItemId}`, {
      method: 'PUT',
      headers: getHeaders(),
      body: { quantity },
    })
    if (result.code !== 200) {
      throw new Error(result.message)
    }
    await fetchCart()
  }

  async function removeItem(cartItemId: number) {
    await $fetch(`${baseURL}/api/cart/${cartItemId}`, {
      method: 'DELETE',
      headers: getHeaders(),
    })
    await fetchCart()
  }

  return { items, loading, totalCount, totalAmount, fetchCart, addToCart, updateQuantity, removeItem }
})
// AI-GENERATED-END
