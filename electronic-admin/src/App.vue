<template>
  <a-config-provider>
    <a-layout class="app-layout">
      <a-layout-sider v-model:collapsed="collapsed" collapsible theme="dark">
        <div class="logo">
          <span v-if="!collapsed">🛒 电商后台</span>
          <span v-else>🛒</span>
        </div>
        <a-menu theme="dark" mode="inline" :selected-keys="selectedKeys">
          <a-menu-item key="dashboard" @click="$router.push('/dashboard')">
            <DashboardOutlined />
            <span>仪表盘</span>
          </a-menu-item>
          <a-menu-item key="products" @click="$router.push('/products')">
            <ShoppingOutlined />
            <span>商品管理</span>
          </a-menu-item>
        </a-menu>
      </a-layout-sider>

      <a-layout>
        <a-layout-header class="app-header">
          <h2>{{ currentTitle }}</h2>
        </a-layout-header>
        <a-layout-content class="app-content">
          <router-view />
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-config-provider>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { DashboardOutlined, ShoppingOutlined } from '@ant-design/icons-vue'

const collapsed = ref(false)
const route = useRoute()

const selectedKeys = computed(() => {
  const path = route.path
  if (path.startsWith('/products')) return ['products']
  return ['dashboard']
})

const currentTitle = computed(() => {
  return (route.meta.title as string) || '电商后台管理'
})
</script>

<style>
body {
  margin: 0;
}
.app-layout {
  min-height: 100vh;
}
.logo {
  height: 48px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}
.app-header {
  background: #fff;
  padding: 0 24px;
  display: flex;
  align-items: center;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
}
.app-header h2 {
  margin: 0;
  font-size: 18px;
}
.app-content {
  margin: 24px;
  padding: 24px;
  background: #fff;
  border-radius: 8px;
  min-height: 280px;
}
</style>
