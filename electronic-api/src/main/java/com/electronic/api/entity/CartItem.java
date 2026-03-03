/**
 * 文件名称：CartItem.java
 * 描述：购物车条目实体类
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.repository.CartItemRepository
 */
package com.electronic.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
@Data
@Entity
@Table(name = "cart_item")
@Schema(description = "购物车条目")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "购物车条目ID")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "用户ID")
    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    @Schema(description = "商品")
    private Product product;

    @Column(nullable = false)
    @Schema(description = "数量")
    private Integer quantity;

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
