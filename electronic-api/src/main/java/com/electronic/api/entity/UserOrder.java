/**
 * 文件名称：UserOrder.java
 * 描述：订单实体类
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.repository.OrderRepository
 */
package com.electronic.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
@Data
@Entity
@Table(name = "user_order")
@Schema(description = "订单实体")
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "订单ID")
    private Long id;

    @Column(nullable = false, unique = true, length = 32)
    @Schema(description = "订单编号")
    private String orderNo;

    @Column(nullable = false)
    @Schema(description = "用户ID")
    private Long userId;

    @Column(nullable = false, precision = 12, scale = 2)
    @Schema(description = "订单总金额")
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Schema(description = "订单状态")
    private OrderStatus status = OrderStatus.PENDING;

    @Column(nullable = false, length = 50)
    @Schema(description = "收货人姓名")
    private String receiverName;

    @Column(nullable = false, length = 11)
    @Schema(description = "收货人电话")
    private String receiverPhone;

    @Column(nullable = false, length = 500)
    @Schema(description = "收货地址")
    private String receiverAddress;

    @Column(length = 500)
    @Schema(description = "订单备注")
    private String remark;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Schema(description = "订单项列表")
    private List<OrderItem> items;

    @Column(updatable = false)
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
// AI-GENERATED-END
