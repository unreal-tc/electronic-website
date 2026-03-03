/**
 * 文件名称：useApi.ts
 * 描述：API 请求组合函数，封装与后端 API 的通信
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see pages/index.vue
 */

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
interface ApiResult<T> {
  code: number
  message: string
  data: T
}

export interface Product {
  id: number
  name: string
  description: string
  price: number
  stock: number
  imageUrl: string
  category: string
  enabled: boolean
  createdAt: string
  updatedAt: string
}

export function useApi() {
  const config = useRuntimeConfig()
  const baseURL = config.public.apiBase

  async function fetchProducts(): Promise<Product[]> {
    try {
      const result = await $fetch<ApiResult<Product[]>>(`${baseURL}/api/products`)
      return result.data ?? []
    }
    catch {
      return []
    }
  }

  async function fetchProduct(id: number): Promise<Product | null> {
    try {
      const result = await $fetch<ApiResult<Product>>(`${baseURL}/api/products/${id}`)
      return result.data ?? null
    }
    catch {
      return null
    }
  }

  async function searchProducts(keyword: string): Promise<Product[]> {
    try {
      const result = await $fetch<ApiResult<Product[]>>(`${baseURL}/api/products/search`, {
        params: { keyword },
      })
      return result.data ?? []
    }
    catch {
      return []
    }
  }

  // @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude，2026-03-03 新增按分类查询商品方法
  async function fetchProductsByCategory(category: string): Promise<Product[]> {
    try {
      const result = await $fetch<ApiResult<Product[]>>(`${baseURL}/api/products/category/${encodeURIComponent(category)}`)
      return result.data ?? []
    }
    catch {
      return []
    }
  }

  return { fetchProducts, fetchProduct, searchProducts, fetchProductsByCategory }
}
// AI-GENERATED-END
