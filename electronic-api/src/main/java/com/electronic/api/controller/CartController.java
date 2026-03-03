/**
 * 文件名称：CartController.java
 * 描述：购物车接口控制器
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.service.CartService
 */
package com.electronic.api.controller;

import com.electronic.api.common.Result;
import com.electronic.api.entity.CartItem;
import com.electronic.api.entity.User;
import com.electronic.api.service.CartService;
import com.electronic.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "购物车", description = "购物车增删改查接口")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @GetMapping
    @Operation(summary = "获取购物车列表")
    public Result<List<CartItem>> list(HttpServletRequest request) {
        Long userId = getCurrentUserId(request);
        return Result.success(cartService.getCartItems(userId));
    }

    @PostMapping
    @Operation(summary = "添加商品到购物车")
    public Result<CartItem> add(HttpServletRequest request,
                                @RequestBody CartAddRequest req) {
        Long userId = getCurrentUserId(request);
        return Result.success(cartService.addToCart(userId, req.getProductId(), req.getQuantity()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新购物车商品数量")
    public Result<CartItem> update(HttpServletRequest request,
                                   @PathVariable Long id,
                                   @RequestBody CartUpdateRequest req) {
        Long userId = getCurrentUserId(request);
        return Result.success(cartService.updateQuantity(userId, id, req.getQuantity()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除购物车商品")
    public Result<Void> remove(HttpServletRequest request, @PathVariable Long id) {
        Long userId = getCurrentUserId(request);
        cartService.removeFromCart(userId, id);
        return Result.success();
    }

    @GetMapping("/count")
    @Operation(summary = "获取购物车数量")
    public Result<Long> count(HttpServletRequest request) {
        Long userId = getCurrentUserId(request);
        return Result.success(cartService.getCartCount(userId));
    }

    private Long getCurrentUserId(HttpServletRequest request) {
        User user = userService.getUserByToken(UserController.extractToken(request));
        return user.getId();
    }

    @Data
    public static class CartAddRequest {
        private Long productId;
        private Integer quantity;
    }

    @Data
    public static class CartUpdateRequest {
        private Integer quantity;
    }
}
// AI-GENERATED-END
