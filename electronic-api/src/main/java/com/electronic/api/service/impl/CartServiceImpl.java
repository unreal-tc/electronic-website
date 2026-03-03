/**
 * 文件名称：CartServiceImpl.java
 * 描述：购物车服务实现类
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.service.CartService
 */
package com.electronic.api.service.impl;

import com.electronic.api.entity.CartItem;
import com.electronic.api.entity.Product;
import com.electronic.api.repository.CartItemRepository;
import com.electronic.api.repository.ProductRepository;
import com.electronic.api.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Override
    public List<CartItem> getCartItems(Long userId) {
        return cartItemRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    @Transactional
    public CartItem addToCart(Long userId, Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("商品不存在"));
        if (!product.getEnabled()) {
            throw new RuntimeException("商品已下架");
        }
        if (quantity <= 0) {
            throw new RuntimeException("数量必须大于0");
        }

        var existing = cartItemRepository.findByUserIdAndProductId(userId, productId);
        if (existing.isPresent()) {
            CartItem item = existing.get();
            int newQty = item.getQuantity() + quantity;
            if (newQty > product.getStock()) {
                throw new RuntimeException("库存不足");
            }
            item.setQuantity(newQty);
            return cartItemRepository.save(item);
        }

        if (quantity > product.getStock()) {
            throw new RuntimeException("库存不足");
        }
        CartItem item = new CartItem();
        item.setUserId(userId);
        item.setProduct(product);
        item.setQuantity(quantity);
        return cartItemRepository.save(item);
    }

    @Override
    @Transactional
    public CartItem updateQuantity(Long userId, Long cartItemId, Integer quantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("购物车条目不存在"));
        if (!item.getUserId().equals(userId)) {
            throw new RuntimeException("无权操作");
        }
        if (quantity <= 0) {
            throw new RuntimeException("数量必须大于0");
        }
        if (quantity > item.getProduct().getStock()) {
            throw new RuntimeException("库存不足");
        }
        item.setQuantity(quantity);
        return cartItemRepository.save(item);
    }

    @Override
    @Transactional
    public void removeFromCart(Long userId, Long cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("购物车条目不存在"));
        if (!item.getUserId().equals(userId)) {
            throw new RuntimeException("无权操作");
        }
        cartItemRepository.delete(item);
    }

    @Override
    public long getCartCount(Long userId) {
        return cartItemRepository.countByUserId(userId);
    }
}
// AI-GENERATED-END
