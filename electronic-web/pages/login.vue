<!--
  文件名称：login.vue
  描述：用户登录/注册页面，支持大陆手机号注册登录

  @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
  @since 2026-03-03
  @see stores/auth.ts
-->

<!-- AI-GENERATED-BEGIN -->
<!-- GENERATED-BY-Claude -->
<template>
  <div class="min-h-[80vh] flex items-center justify-center py-12 px-4">
    <div class="bg-white rounded-2xl shadow-lg p-8 w-full max-w-md">
      <h1 class="text-2xl font-bold text-center mb-8">
        {{ isRegister ? '注册账号' : '登录' }}
      </h1>

      <form @submit.prevent="handleSubmit">
        <div class="space-y-5">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">手机号</label>
            <input
              v-model="phone"
              type="tel"
              maxlength="11"
              placeholder="请输入大陆手机号"
              class="w-full border border-gray-300 rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition"
              :class="{ 'border-red-400': phoneError }"
            >
            <p v-if="phoneError" class="text-red-500 text-xs mt-1">
              {{ phoneError }}
            </p>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">密码</label>
            <input
              v-model="password"
              type="password"
              placeholder="请输入密码（至少6位）"
              class="w-full border border-gray-300 rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition"
            >
          </div>

          <div v-if="isRegister">
            <label class="block text-sm font-medium text-gray-700 mb-1">昵称（选填）</label>
            <input
              v-model="nickname"
              type="text"
              placeholder="请输入昵称"
              class="w-full border border-gray-300 rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition"
            >
          </div>

          <p v-if="errorMsg" class="text-red-500 text-sm text-center">
            {{ errorMsg }}
          </p>

          <button
            type="submit"
            class="w-full bg-primary text-white py-3 rounded-lg text-lg font-semibold hover:opacity-90 transition disabled:opacity-50"
            :disabled="submitting"
          >
            {{ submitting ? '处理中...' : (isRegister ? '注册' : '登录') }}
          </button>
        </div>
      </form>

      <p class="text-center text-sm text-gray-500 mt-6">
        {{ isRegister ? '已有账号？' : '没有账号？' }}
        <button
          class="text-primary hover:underline font-medium"
          @click="toggleMode"
        >
          {{ isRegister ? '去登录' : '去注册' }}
        </button>
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '~/stores/auth'

const authStore = useAuthStore()
const router = useRouter()
const route = useRoute()

const isRegister = ref(false)
const phone = ref('')
const password = ref('')
const nickname = ref('')
const errorMsg = ref('')
const submitting = ref(false)

const phoneError = computed(() => {
  if (!phone.value) return ''
  if (!/^1[3-9]\d{0,9}$/.test(phone.value)) return '请输入正确的大陆手机号'
  return ''
})

function toggleMode() {
  isRegister.value = !isRegister.value
  errorMsg.value = ''
}

async function handleSubmit() {
  errorMsg.value = ''
  if (!phone.value || phone.value.length !== 11 || !/^1[3-9]\d{9}$/.test(phone.value)) {
    errorMsg.value = '请输入正确的11位大陆手机号'
    return
  }
  if (!password.value || password.value.length < 6) {
    errorMsg.value = '密码长度不能少于6位'
    return
  }

  submitting.value = true
  try {
    if (isRegister.value) {
      await authStore.register(phone.value, password.value, nickname.value || undefined)
    }
    else {
      await authStore.login(phone.value, password.value)
    }
    const redirect = (route.query.redirect as string) || '/'
    router.push(redirect)
  }
  catch (e: unknown) {
    const err = e as Error
    errorMsg.value = err.message || '操作失败，请重试'
  }
  finally {
    submitting.value = false
  }
}

if (authStore.isLoggedIn) {
  router.push('/')
}
</script>
<!-- AI-GENERATED-END -->
