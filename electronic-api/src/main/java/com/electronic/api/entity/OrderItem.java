/**
 * 文件名称：OrderItem.java
 * 描述：订单项实体类，保存下单时的商品快照信息
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.entity.UserOrder
 */
package com.electronic.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
@Data
@Entity
@Table(name = "order_item")
@Schema(description = "订单项")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "订单项ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private UserOrder order;

    @Column(nullable = false)
    @Schema(description = "商品ID")
    private Long productId;

    @Column(nullable = false, length = 200)
    @Schema(description = "商品名称（下单快照）")
    private String productName;

    @Column(length = 500)
    @Schema(description = "商品图片（下单快照）")
    private String productImageUrl;

    @Column(nullable = false, precision = 10, scale = 2)
    @Schema(description = "商品单价（下单快照）")
    private BigDecimal productPrice;

    @Column(nullable = false)
    @Schema(description = "购买数量")
    private Integer quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    @Schema(description = "小计金额")
    private BigDecimal subtotal;
}
// AI-GENERATED-END
