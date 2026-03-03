/**
 * 文件名称：OrderServiceImpl.java
 * 描述：订单服务实现类，处理下单、查询等业务
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.service.OrderService
 */
package com.electronic.api.service.impl;

import com.electronic.api.entity.*;
import com.electronic.api.repository.CartItemRepository;
import com.electronic.api.repository.OrderRepository;
import com.electronic.api.repository.ProductRepository;
import com.electronic.api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public UserOrder createOrderFromCart(Long userId, List<Long> cartItemIds,
                                         String receiverName, String receiverPhone,
                                         String receiverAddress, String remark) {
        List<CartItem> cartItems = cartItemRepository.findAllById(cartItemIds);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("购物车条目不存在");
        }
        for (CartItem ci : cartItems) {
            if (!ci.getUserId().equals(userId)) {
                throw new RuntimeException("无权操作");
            }
        }

        UserOrder order = new UserOrder();
        order.setOrderNo(generateOrderNo());
        order.setUserId(userId);
        order.setReceiverName(receiverName);
        order.setReceiverPhone(receiverPhone);
        order.setReceiverAddress(receiverAddress);
        order.setRemark(remark);
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem ci : cartItems) {
            Product product = ci.getProduct();
            if (ci.getQuantity() > product.getStock()) {
                throw new RuntimeException("商品「" + product.getName() + "」库存不足");
            }
            product.setStock(product.getStock() - ci.getQuantity());
            productRepository.save(product);

            OrderItem oi = buildOrderItem(order, product, ci.getQuantity());
            orderItems.add(oi);
            total = total.add(oi.getSubtotal());
        }

        order.setTotalAmount(total);
        order.setItems(orderItems);
        UserOrder saved = orderRepository.save(order);

        cartItemRepository.deleteAll(cartItems);
        return saved;
    }

    @Override
    @Transactional
    public UserOrder createDirectOrder(Long userId, Long productId, Integer quantity,
                                        String receiverName, String receiverPhone,
                                        String receiverAddress, String remark) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("商品不存在"));
        if (!product.getEnabled()) {
            throw new RuntimeException("商品已下架");
        }
        if (quantity > product.getStock()) {
            throw new RuntimeException("库存不足");
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        UserOrder order = new UserOrder();
        order.setOrderNo(generateOrderNo());
        order.setUserId(userId);
        order.setReceiverName(receiverName);
        order.setReceiverPhone(receiverPhone);
        order.setReceiverAddress(receiverAddress);
        order.setRemark(remark);
        order.setStatus(OrderStatus.PENDING);

        OrderItem oi = buildOrderItem(order, product, quantity);
        order.setItems(List.of(oi));
        order.setTotalAmount(oi.getSubtotal());

        return orderRepository.save(order);
    }

    @Override
    public List<UserOrder> getUserOrders(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public UserOrder getOrderDetail(Long userId, Long orderId) {
        return orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new RuntimeException("订单不存在"));
    }

    @Override
    public List<UserOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public UserOrder updateOrderStatus(Long orderId, String status) {
        UserOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在"));
        order.setStatus(OrderStatus.valueOf(status));
        return orderRepository.save(order);
    }

    private OrderItem buildOrderItem(UserOrder order, Product product, int qty) {
        OrderItem oi = new OrderItem();
        oi.setOrder(order);
        oi.setProductId(product.getId());
        oi.setProductName(product.getName());
        oi.setProductImageUrl(product.getImageUrl());
        oi.setProductPrice(product.getPrice());
        oi.setQuantity(qty);
        oi.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(qty)));
        return oi;
    }

    private String generateOrderNo() {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int random = ThreadLocalRandom.current().nextInt(1000, 9999);
        return timestamp + random;
    }
}
// AI-GENERATED-END
