/**
 * 文件名称：product.ts
 * 描述：商品状态管理
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see src/api/product.ts
 */

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getProducts, type Product } from '../api/product'

export const useProductStore = defineStore('product', () => {
  const products = ref<Product[]>([])
  const loading = ref(false)

  async function loadProducts() {
    loading.value = true
    try {
      products.value = await getProducts()
    } finally {
      loading.value = false
    }
  }

  return { products, loading, loadProducts }
})
// AI-GENERATED-END
