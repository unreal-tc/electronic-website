/**
 * 文件名称：index.ts
 * 描述：路由配置
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see src/views
 */

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      redirect: '/dashboard',
    },
    {
      path: '/dashboard',
      name: 'Dashboard',
      component: () => import('../views/Dashboard.vue'),
      meta: { title: '仪表盘' },
    },
    {
      path: '/products',
      name: 'Products',
      component: () => import('../views/ProductList.vue'),
      meta: { title: '商品管理' },
    },
  ],
})

export default router
// AI-GENERATED-END
