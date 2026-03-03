/**
 * 文件名称：main.ts
 * 描述：EB后台管理应用入口
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see src/App.vue
 */

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'
import App from './App.vue'
import router from './router'

const app = createApp(App)
app.use(createPinia())
app.use(router)
app.use(Antd)
app.mount('#app')
// AI-GENERATED-END
