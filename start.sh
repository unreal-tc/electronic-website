#!/usr/bin/env bash
# 文件名称：start.sh
# 描述：一键部署并启动电商网站三个服务（API + C端前端 + 后台管理）
#
# @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
# @since 2026-03-03

# AI-GENERATED-BEGIN
# GENERATED-BY-Claude

set -uo pipefail

ROOT_DIR="$(cd "$(dirname "$0")" && pwd)"
LOG_DIR="$ROOT_DIR/.logs"
PID_FILE="$LOG_DIR/.service_pids"
mkdir -p "$LOG_DIR"

API_LOG="$LOG_DIR/api.log"
WEB_LOG="$LOG_DIR/web.log"
ADMIN_LOG="$LOG_DIR/admin.log"

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
BOLD='\033[1m'
NC='\033[0m'

do_stop() {
    bash "$ROOT_DIR/stop.sh" 2>/dev/null
}

trap 'do_stop; exit 0' INT TERM

print_banner() {
    echo -e "${CYAN}${BOLD}"
    echo "╔══════════════════════════════════════════════════╗"
    echo "║          电商网站 - 一键启动脚本                ║"
    echo "╠══════════════════════════════════════════════════╣"
    echo "║  API 服务      :  http://localhost:8080         ║"
    echo "║  API 文档      :  http://localhost:8080/doc.html║"
    echo "║  C端前端       :  http://localhost:3000         ║"
    echo "║  后台管理      :  http://localhost:3001         ║"
    echo "╚══════════════════════════════════════════════════╝"
    echo -e "${NC}"
}

check_port() {
    local port=$1
    if command -v ss &>/dev/null; then
        ss -tlnp 2>/dev/null | grep -q ":${port} "
    elif command -v lsof &>/dev/null; then
        lsof -i :"$port" -sTCP:LISTEN &>/dev/null
    elif command -v netstat &>/dev/null; then
        netstat -tlnp 2>/dev/null | grep -q ":${port} "
    else
        return 1
    fi
}

check_prerequisites() {
    echo -e "${BOLD}[1/4] 检查环境依赖...${NC}"
    local missing=0

    if command -v java &>/dev/null; then
        local java_ver
        java_ver=$(java -version 2>&1 | head -1)
        echo -e "  ${GREEN}✓${NC} Java: $java_ver"
    else
        echo -e "  ${RED}✗${NC} Java 未安装（需要 JDK 17+）"
        missing=1
    fi

    if command -v mvn &>/dev/null; then
        local mvn_ver
        mvn_ver=$(mvn -version 2>&1 | head -1)
        echo -e "  ${GREEN}✓${NC} Maven: $mvn_ver"
    else
        echo -e "  ${RED}✗${NC} Maven 未安装"
        echo -e "      ${YELLOW}提示：sudo apt-get install -y maven${NC}"
        missing=1
    fi

    if command -v node &>/dev/null; then
        echo -e "  ${GREEN}✓${NC} Node.js: $(node -v)"
    else
        echo -e "  ${RED}✗${NC} Node.js 未安装（需要 18+）"
        missing=1
    fi

    if command -v pnpm &>/dev/null; then
        echo -e "  ${GREEN}✓${NC} pnpm: $(pnpm -v)"
    else
        echo -e "  ${RED}✗${NC} pnpm 未安装（需要 9+）"
        echo -e "      ${YELLOW}提示：npm install -g pnpm${NC}"
        missing=1
    fi

    if [ $missing -ne 0 ]; then
        echo -e "\n${RED}缺少必要依赖，请先安装后再试。${NC}"
        exit 1
    fi

    for port in 8080 3000 3001; do
        if check_port "$port"; then
            echo -e "  ${RED}✗${NC} 端口 $port 已被占用，请先释放该端口"
            missing=1
        fi
    done

    if [ $missing -ne 0 ]; then
        exit 1
    fi

    echo ""
}

install_deps() {
    echo -e "${BOLD}[2/4] 安装前端依赖...${NC}"

    if [ ! -d "$ROOT_DIR/electronic-web/node_modules" ]; then
        echo -e "  ${CYAN}→${NC} 安装 electronic-web 依赖..."
        (cd "$ROOT_DIR/electronic-web" && pnpm install --frozen-lockfile 2>&1) | tail -3
    else
        echo -e "  ${GREEN}✓${NC} electronic-web 依赖已就绪"
    fi

    if [ ! -d "$ROOT_DIR/electronic-admin/node_modules" ]; then
        echo -e "  ${CYAN}→${NC} 安装 electronic-admin 依赖..."
        (cd "$ROOT_DIR/electronic-admin" && pnpm install --frozen-lockfile 2>&1) | tail -3
    else
        echo -e "  ${GREEN}✓${NC} electronic-admin 依赖已就绪"
    fi

    echo ""
}

start_api() {
    echo -e "${BOLD}[3/4] 启动 API 服务 (Spring Boot)...${NC}"
    echo -e "  ${CYAN}→${NC} 编译并启动中，日志输出到 ${API_LOG}"

    setsid bash -c "cd '$ROOT_DIR/electronic-api' && exec mvn spring-boot:run -q" > "$API_LOG" 2>&1 &
    local pid=$!
    local pgid
    pgid=$(ps -o pgid= -p "$pid" 2>/dev/null | tr -d ' ')
    echo "API $pgid" >> "$PID_FILE"
    echo -e "  ${CYAN}→${NC} API 进程组 PGID: $pgid"

    local max_wait=120
    local waited=0
    echo -ne "  ${CYAN}→${NC} 等待 API 就绪"
    while [ $waited -lt $max_wait ]; do
        if ! kill -0 "$pid" 2>/dev/null; then
            echo ""
            echo -e "  ${RED}✗${NC} API 服务启动失败，请查看日志："
            echo -e "      ${YELLOW}cat $API_LOG${NC}"
            tail -20 "$API_LOG"
            exit 1
        fi

        if curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/api/products 2>/dev/null | grep -q "200"; then
            echo ""
            echo -e "  ${GREEN}✓${NC} API 服务已就绪 (耗时 ${waited}s)"
            break
        fi

        echo -n "."
        sleep 2
        waited=$((waited + 2))
    done

    if [ $waited -ge $max_wait ]; then
        echo ""
        echo -e "  ${RED}✗${NC} API 服务启动超时 (${max_wait}s)，请查看日志："
        echo -e "      ${YELLOW}cat $API_LOG${NC}"
        exit 1
    fi

    echo ""
}

start_frontends() {
    echo -e "${BOLD}[4/4] 启动前端服务...${NC}"

    setsid bash -c "cd '$ROOT_DIR/electronic-web' && exec pnpm dev" > "$WEB_LOG" 2>&1 &
    local web_pid=$!
    local web_pgid
    web_pgid=$(ps -o pgid= -p "$web_pid" 2>/dev/null | tr -d ' ')
    echo "WEB $web_pgid" >> "$PID_FILE"
    echo -e "  ${GREEN}✓${NC} C端前端已启动 (PGID: $web_pgid, 端口: 3000)"

    setsid bash -c "cd '$ROOT_DIR/electronic-admin' && exec pnpm dev" > "$ADMIN_LOG" 2>&1 &
    local admin_pid=$!
    local admin_pgid
    admin_pgid=$(ps -o pgid= -p "$admin_pid" 2>/dev/null | tr -d ' ')
    echo "ADMIN $admin_pgid" >> "$PID_FILE"
    echo -e "  ${GREEN}✓${NC} 后台管理已启动 (PGID: $admin_pgid, 端口: 3001)"

    echo ""
}

rm -f "$PID_FILE"

print_banner
check_prerequisites
install_deps
start_api
start_frontends

echo -e "${GREEN}${BOLD}════════════════════════════════════════════════════${NC}"
echo -e "${GREEN}${BOLD}  所有服务已成功启动！${NC}"
echo -e "${GREEN}${BOLD}════════════════════════════════════════════════════${NC}"
echo ""
echo -e "  ${CYAN}C端前端${NC}      →  http://localhost:3000"
echo -e "  ${CYAN}后台管理${NC}      →  http://localhost:3001"
echo -e "  ${CYAN}API 接口${NC}      →  http://localhost:8080/api/products"
echo -e "  ${CYAN}API 文档${NC}      →  http://localhost:8080/doc.html"
echo -e "  ${CYAN}H2 控制台${NC}     →  http://localhost:8080/h2-console"
echo ""
echo -e "  ${YELLOW}日志文件：${NC}"
echo -e "    API   → $API_LOG"
echo -e "    Web   → $WEB_LOG"
echo -e "    Admin → $ADMIN_LOG"
echo ""
echo -e "  ${YELLOW}停止服务：按 Ctrl+C 或运行 ./stop.sh${NC}"
echo ""

while true; do
    sleep 5 &
    wait $! 2>/dev/null || break
done

# AI-GENERATED-END
