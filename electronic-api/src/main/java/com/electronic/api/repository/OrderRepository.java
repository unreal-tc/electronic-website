/**
 * 文件名称：OrderRepository.java
 * 描述：订单数据访问层
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.entity.UserOrder
 */
package com.electronic.api.repository;

import com.electronic.api.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
@Repository
public interface OrderRepository extends JpaRepository<UserOrder, Long> {

    List<UserOrder> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<UserOrder> findByIdAndUserId(Long id, Long userId);

    Optional<UserOrder> findByOrderNo(String orderNo);
}
// AI-GENERATED-END
