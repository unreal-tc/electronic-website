# electronic-website

电商网站 - 前后端分离架构

## 项目结构

| 服务 | 目录 | 技术栈 | 端口 |
|------|------|--------|------|
| C端前端 | `electronic-web/` | Nuxt 3 + Vue 3 + TypeScript + Tailwind CSS + Pinia | 3000 |
| EB后台管理 | `electronic-admin/` | Vue 3 + Vite + TypeScript + Ant Design Vue + Pinia | 3001 |
| Java API | `electronic-api/` | Spring Boot 3 + Spring Data JPA + H2 + Knife4j | 8080 |

## 快速开始

### 环境要求

- Node.js 18+
- pnpm 9+
- JDK 17+ (推荐 21)
- Maven 3.8+

### 启动服务

```bash
# 1. 启动 API 服务
cd electronic-api
mvn spring-boot:run

# 2. 启动 C端前端
cd electronic-web
pnpm install
pnpm dev

# 3. 启动 EB后台管理
cd electronic-admin
pnpm install
pnpm dev
```

### 访问地址

- C端前端: http://localhost:3000
- EB后台管理: http://localhost:3001
- API 接口: http://localhost:8080/api/products
- API 文档 (Knife4j): http://localhost:8080/doc.html
- H2 控制台: http://localhost:8080/h2-console

## License

Apache License 2.0
