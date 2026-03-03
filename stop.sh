#!/usr/bin/env bash
# 文件名称：stop.sh
# 描述：停止电商网站所有服务
#
# @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
# @since 2026-03-03

# AI-GENERATED-BEGIN
# GENERATED-BY-Claude

ROOT_DIR="$(cd "$(dirname "$0")" && pwd)"
PID_FILE="$ROOT_DIR/.logs/.service_pids"

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

OS_TYPE="$(uname -s)"

get_children() {
    local parent=$1
    if [ "$OS_TYPE" = "Darwin" ]; then
        ps -o pid= -ax | while read -r p; do
            ps -o ppid= -p "$p" 2>/dev/null | tr -d ' ' | grep -qx "$parent" && echo "$p"
        done
    else
        ps -o pid= --ppid "$parent" 2>/dev/null
    fi
}

kill_tree() {
    local pid=$1
    local sig=${2:-TERM}
    local children
    children=$(get_children "$pid")
    for child in $children; do
        kill_tree "$child" "$sig"
    done
    kill -"$sig" "$pid" 2>/dev/null || true
}

if [ ! -f "$PID_FILE" ]; then
    echo -e "${YELLOW}没有发现运行中的服务（PID 文件不存在）${NC}"
    exit 0
fi

echo -e "${YELLOW}正在停止所有服务...${NC}"

while read -r name pid; do
    if [ -n "$pid" ] && kill -0 "$pid" 2>/dev/null; then
        kill_tree "$pid" TERM
        echo -e "  ${GREEN}✓${NC} ${name} 已发送停止信号 (PID: $pid)"
    else
        echo -e "  ${YELLOW}-${NC} ${name} 已经停止"
    fi
done < "$PID_FILE"

sleep 2

while read -r name pid; do
    if [ -n "$pid" ] && kill -0 "$pid" 2>/dev/null; then
        kill_tree "$pid" 9
        echo -e "  ${YELLOW}!${NC} ${name} 已强制终止 (PID: $pid)"
    fi
done < "$PID_FILE"

rm -f "$PID_FILE"
echo -e "${GREEN}所有服务已停止。${NC}"

# AI-GENERATED-END
