/**
 * 文件名称：Product.java
 * 描述：商品实体类
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.repository.ProductRepository
 */
package com.electronic.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
@Data
@Entity
@Table(name = "product")
@Schema(description = "商品实体")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "商品ID")
    private Long id;

    @Column(nullable = false, length = 200)
    @Schema(description = "商品名称")
    private String name;

    @Column(length = 1000)
    @Schema(description = "商品描述")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    @Schema(description = "商品价格")
    private BigDecimal price;

    @Column(nullable = false)
    @Schema(description = "库存数量")
    private Integer stock;

    @Column(length = 500)
    @Schema(description = "商品图片URL")
    private String imageUrl;

    @Column(length = 100)
    @Schema(description = "商品分类")
    private String category;

    @Column(nullable = false)
    @Schema(description = "是否上架")
    private Boolean enabled = true;

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
