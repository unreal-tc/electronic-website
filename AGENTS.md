# AGENTS.md

## Cursor Cloud specific instructions

此仓库 (`electronic-website` / 电商网站) 包含三个服务，采用前后端分离架构。

### 服务概览

| 服务 | 目录 | 技术栈 | 端口 |
|------|------|--------|------|
| C端前端 | `electronic-web/` | Nuxt 3 + Vue 3 + TypeScript + Tailwind CSS | 3000 |
| EB后台管理 | `electronic-admin/` | Vue 3 + Vite + TypeScript + Ant Design Vue | 3001 |
| Java API | `electronic-api/` | Spring Boot 3 + JPA + H2 + Knife4j | 8080 |

### 启动顺序

必须先启动 API 服务（后端），再启动前端服务：

1. `cd electronic-api && mvn spring-boot:run`
2. `cd electronic-web && pnpm dev`
3. `cd electronic-admin && pnpm dev`

### 非显而易见的注意事项

- **H2 内存数据库**：开发环境使用 H2 内存数据库，每次重启 API 服务数据会重置。`data.sql` 会自动加载初始商品数据。
- **JPA 延迟初始化**：`application.yml` 中配置了 `defer-datasource-initialization: true`，确保 JPA 建表后再执行 `data.sql`。
- **CORS 已配置**：API 服务允许所有跨域请求（开发模式），前端可直接调用。
- **Admin API 代理**：`electronic-admin` 的 Vite 配置了 `/api` 路径代理到 `localhost:8080`，无需处理跨域。
- **Nuxt SSR 与 API**：C端首页在 SSR 模式下从服务端获取 API 数据时，如果 API 未启动会降级为客户端渲染。
- **pnpm build scripts**：两个前端项目的 `package.json` 中已配置 `pnpm.onlyBuiltDependencies`，避免交互式 `pnpm approve-builds`。
- **Knife4j API 文档**：启动 API 后访问 `http://localhost:8080/doc.html` 查看接口文档。
- **Maven 需要安装**：环境中默认没有 Maven，需要 `sudo apt-get install -y maven` 安装。
- **ESLint**：C端前端可用 `pnpm lint` 运行 ESLint 检查。Admin 项目通过 `pnpm build`（含 vue-tsc 类型检查）验证。
- **ZKT AI 注释规范**：所有 AI 生成代码需遵循 workspace rules 中的注释规范（`AI-GENERATED-BEGIN/END`、文件头注释等）。
