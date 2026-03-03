/**
 * 文件名称：User.java
 * 描述：用户实体类，支持手机号注册登录
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.repository.UserRepository
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
@Table(name = "app_user")
@Schema(description = "用户实体")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "用户ID")
    private Long id;

    @Column(nullable = false, unique = true, length = 11)
    @Schema(description = "手机号")
    private String phone;

    @Column(nullable = false, length = 100)
    @Schema(description = "密码", hidden = true)
    private String password;

    @Column(length = 50)
    @Schema(description = "昵称")
    private String nickname;

    @Column(length = 500)
    @Schema(description = "头像URL")
    private String avatar;

    @Column(length = 100)
    @Schema(description = "登录令牌", hidden = true)
    private String token;

    @Column(nullable = false)
    @Schema(description = "是否启用")
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
