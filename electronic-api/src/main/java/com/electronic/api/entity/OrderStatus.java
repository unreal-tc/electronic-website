/**
 * 文件名称：OrderStatus.java
 * 描述：订单状态枚举
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.entity.UserOrder
 */
package com.electronic.api.entity;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
public enum OrderStatus {
    PENDING("待付款"),
    PAID("已付款"),
    SHIPPED("已发货"),
    COMPLETED("已完成"),
    CANCELLED("已取消");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
// AI-GENERATED-END
