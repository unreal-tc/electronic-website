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

if [ ! -f "$PID_FILE" ]; then
    echo -e "${YELLOW}没有发现运行中的服务（PID 文件不存在）${NC}"
    exit 0
fi

echo -e "${YELLOW}正在停止所有服务...${NC}"

while read -r name pgid; do
    if [ -n "$pgid" ]; then
        if kill -0 -- -"$pgid" 2>/dev/null; then
            kill -- -"$pgid" 2>/dev/null || true
            echo -e "  ${GREEN}✓${NC} ${name} 已发送停止信号 (PGID: $pgid)"
        else
            echo -e "  ${YELLOW}-${NC} ${name} 已经停止 (PGID: $pgid)"
        fi
    fi
done < "$PID_FILE"

sleep 2

while read -r name pgid; do
    if [ -n "$pgid" ] && kill -0 -- -"$pgid" 2>/dev/null; then
        kill -9 -- -"$pgid" 2>/dev/null || true
        echo -e "  ${YELLOW}!${NC} ${name} 已强制终止 (PGID: $pgid)"
    fi
done < "$PID_FILE"

rm -f "$PID_FILE"
echo -e "${GREEN}所有服务已停止。${NC}"

# AI-GENERATED-END
