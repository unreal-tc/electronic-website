/**
 * 文件名称：CartItemRepository.java
 * 描述：购物车数据访问层
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.entity.CartItem
 */
package com.electronic.api.repository;

import com.electronic.api.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);

    void deleteByUserIdAndIdIn(Long userId, List<Long> ids);

    long countByUserId(Long userId);
}
// AI-GENERATED-END
