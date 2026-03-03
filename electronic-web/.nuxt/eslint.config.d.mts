import type { FlatConfigComposer } from "../node_modules/.pnpm/eslint-flat-config-utils@3.0.1/node_modules/eslint-flat-config-utils/dist/index.mjs"
import { defineFlatConfigs } from "../node_modules/.pnpm/@nuxt+eslint-config@1.15.2_@typescript-eslint+utils@8.56.1_eslint@9.39.3_jiti@2.6.1__ty_b6ab20f060f8ed465b6c3e4dc334e0ee/node_modules/@nuxt/eslint-config/dist/flat.mjs"
import type { NuxtESLintConfigOptionsResolved } from "../node_modules/.pnpm/@nuxt+eslint-config@1.15.2_@typescript-eslint+utils@8.56.1_eslint@9.39.3_jiti@2.6.1__ty_b6ab20f060f8ed465b6c3e4dc334e0ee/node_modules/@nuxt/eslint-config/dist/flat.mjs"

declare const configs: FlatConfigComposer
declare const options: NuxtESLintConfigOptionsResolved
declare const withNuxt: typeof defineFlatConfigs
export default withNuxt
export { withNuxt, defineFlatConfigs, configs, options }