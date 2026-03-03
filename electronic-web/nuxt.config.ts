// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2025-05-04',
  devtools: { enabled: true },

  modules: [
    '@nuxtjs/tailwindcss',
    '@pinia/nuxt',
    '@nuxt/eslint',
  ],

  runtimeConfig: {
    public: {
      apiBase: 'http://localhost:8080',
    },
  },

  app: {
    head: {
      title: '电商网站',
      meta: [
        { charset: 'utf-8' },
        { name: 'viewport', content: 'width=device-width, initial-scale=1' },
        { name: 'description', content: '电商网站 - 优质商品，放心购物' },
      ],
    },
  },
})
