<template>
  <div class="min-h-screen flex flex-col">
    <!-- Header -->
    <header class="bg-white shadow-sm sticky top-0 z-50">
      <div class="max-w-7xl mx-auto px-4 py-3 flex items-center justify-between">
        <NuxtLink to="/" class="text-2xl font-bold text-primary">
          🛒 电商网站
        </NuxtLink>
        <nav class="flex gap-6 text-gray-600">
          <NuxtLink to="/" class="hover:text-primary transition">首页</NuxtLink>
          <NuxtLink to="/products" class="hover:text-primary transition">全部商品</NuxtLink>
        </nav>
      </div>
    </header>

    <!-- Main content -->
    <main class="flex-1">
      <slot />
    </main>

    <!-- Footer -->
    <footer class="bg-gray-800 text-gray-300 py-8">
      <div class="max-w-7xl mx-auto px-4 text-center">
        <p>&copy; 2026 电商网站. All rights reserved.</p>
      </div>
    </footer>
  </div>
</template>
