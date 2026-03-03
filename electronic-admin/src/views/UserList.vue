<!--
  文件名称：UserList.vue
  描述：用户管理页面，支持查看和启用/禁用用户

  @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
  @since 2026-03-03
  @see src/api/user.ts
-->

<!-- AI-GENERATED-BEGIN -->
<!-- GENERATED-BY-Claude -->
<template>
  <div>
    <a-button type="primary" :loading="loading" style="margin-bottom: 16px" @click="loadUsers">
      刷新
    </a-button>

    <a-table
      :columns="columns"
      :data-source="users"
      :loading="loading"
      row-key="id"
      :pagination="{ pageSize: 10 }"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'enabled'">
          <a-tag :color="record.enabled ? 'green' : 'red'">
            {{ record.enabled ? '已启用' : '已禁用' }}
          </a-tag>
        </template>
        <template v-if="column.key === 'createdAt'">
          {{ formatTime(record.createdAt) }}
        </template>
        <template v-if="column.key === 'action'">
          <a-popconfirm
            :title="`确定要${record.enabled ? '禁用' : '启用'}该用户吗？`"
            @confirm="toggleStatus(record)"
          >
            <a-button
              :type="record.enabled ? 'default' : 'primary'"
              size="small"
              :danger="record.enabled"
            >
              {{ record.enabled ? '禁用' : '启用' }}
            </a-button>
          </a-popconfirm>
        </template>
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { getUsers, updateUserStatus, type User } from '../api/user'

const users = ref<User[]>([])
const loading = ref(false)

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 60 },
  { title: '手机号', dataIndex: 'phone', key: 'phone' },
  { title: '昵称', dataIndex: 'nickname', key: 'nickname' },
  { title: '状态', key: 'enabled', width: 100 },
  { title: '注册时间', key: 'createdAt', width: 180 },
  { title: '操作', key: 'action', width: 100 },
]

function formatTime(dt: string) {
  if (!dt) return ''
  return dt.replace('T', ' ').substring(0, 19)
}

async function loadUsers() {
  loading.value = true
  try {
    users.value = await getUsers()
  } catch (e: unknown) {
    message.error('加载用户列表失败')
  } finally {
    loading.value = false
  }
}

async function toggleStatus(record: User) {
  try {
    await updateUserStatus(record.id, !record.enabled)
    message.success(record.enabled ? '已禁用' : '已启用')
    await loadUsers()
  } catch (e: unknown) {
    message.error('操作失败')
  }
}

onMounted(() => {
  loadUsers()
})
</script>
<!-- AI-GENERATED-END -->
