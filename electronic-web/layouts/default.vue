<!--
  文件名称：default.vue
  描述：默认布局，包含导航栏和底部栏

  @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
  @since 2026-03-03
  @see stores/auth.ts
-->

<!-- AI-GENERATED-BEGIN -->
<!-- GENERATED-BY-Claude -->
<template>
  <div class="min-h-screen flex flex-col">
    <header class="bg-white shadow-sm sticky top-0 z-50">
      <div class="max-w-7xl mx-auto px-4 py-3 flex items-center justify-between">
        <NuxtLink to="/" class="text-2xl font-bold text-primary">
          🛒 电商网站
        </NuxtLink>
        <nav class="flex items-center gap-5 text-gray-600">
          <NuxtLink to="/" class="hover:text-primary transition">首页</NuxtLink>
          <NuxtLink to="/products" class="hover:text-primary transition">全部商品</NuxtLink>
          <NuxtLink to="/cart" class="relative hover:text-primary transition">
            购物车
            <span
              v-if="authStore.isLoggedIn && cartStore.totalCount > 0"
              class="absolute -top-2 -right-4 bg-primary text-white text-xs w-5 h-5 rounded-full flex items-center justify-center"
            >
              {{ cartStore.totalCount > 99 ? '99+' : cartStore.totalCount }}
            </span>
          </NuxtLink>
          <template v-if="authStore.isLoggedIn">
            <NuxtLink to="/orders" class="hover:text-primary transition">我的订单</NuxtLink>
            <div class="relative group">
              <button class="flex items-center gap-1 hover:text-primary transition">
                <span class="w-7 h-7 bg-primary text-white rounded-full flex items-center justify-center text-sm">
                  {{ (authStore.user?.nickname || '用')[0] }}
                </span>
                <span class="text-sm">{{ authStore.user?.nickname }}</span>
              </button>
              <div class="absolute right-0 top-full mt-1 bg-white border rounded-lg shadow-lg py-2 w-32 hidden group-hover:block">
                <button
                  class="block w-full text-left px-4 py-2 text-sm text-gray-600 hover:bg-gray-50 hover:text-primary"
                  @click="handleLogout"
                >
                  退出登录
                </button>
              </div>
            </div>
          </template>
          <NuxtLink v-else to="/login" class="bg-primary text-white px-4 py-1.5 rounded-lg text-sm hover:opacity-90 transition">
            登录/注册
          </NuxtLink>
        </nav>
      </div>
    </header>

    <main class="flex-1">
      <slot />
    </main>

    <footer class="bg-gray-800 text-gray-300 py-8">
      <div class="max-w-7xl mx-auto px-4 text-center">
        <p>&copy; 2026 电商网站. All rights reserved.</p>
      </div>
    </footer>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '~/stores/auth'
import { useCartStore } from '~/stores/cart'

const authStore = useAuthStore()
const cartStore = useCartStore()
const router = useRouter()

function handleLogout() {
  authStore.logout()
  cartStore.items = []
  router.push('/')
}

onMounted(() => {
  if (authStore.isLoggedIn) {
    cartStore.fetchCart()
  }
})
</script>
<!-- AI-GENERATED-END -->
