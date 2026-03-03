/**
 * 文件名称：OrderService.java
 * 描述：订单服务接口
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.service.impl.OrderServiceImpl
 */
package com.electronic.api.service;

import com.electronic.api.entity.UserOrder;

import java.util.List;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
public interface OrderService {

    UserOrder createOrderFromCart(Long userId, List<Long> cartItemIds,
                                  String receiverName, String receiverPhone,
                                  String receiverAddress, String remark);

    UserOrder createDirectOrder(Long userId, Long productId, Integer quantity,
                                String receiverName, String receiverPhone,
                                String receiverAddress, String remark);

    List<UserOrder> getUserOrders(Long userId);

    UserOrder getOrderDetail(Long userId, Long orderId);

    List<UserOrder> getAllOrders();

    UserOrder updateOrderStatus(Long orderId, String status);
}
// AI-GENERATED-END
