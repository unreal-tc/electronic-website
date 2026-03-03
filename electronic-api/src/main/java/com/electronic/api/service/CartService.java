/**
 * 文件名称：CartService.java
 * 描述：购物车服务接口
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.service.impl.CartServiceImpl
 */
package com.electronic.api.service;

import com.electronic.api.entity.CartItem;

import java.util.List;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
public interface CartService {

    List<CartItem> getCartItems(Long userId);

    CartItem addToCart(Long userId, Long productId, Integer quantity);

    CartItem updateQuantity(Long userId, Long cartItemId, Integer quantity);

    void removeFromCart(Long userId, Long cartItemId);

    long getCartCount(Long userId);
}
// AI-GENERATED-END
