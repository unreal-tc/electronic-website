/**
 * 文件名称：OrderController.java
 * 描述：订单接口控制器，提供下单、查询等功能
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.service.OrderService
 */
package com.electronic.api.controller;

import com.electronic.api.common.Result;
import com.electronic.api.entity.User;
import com.electronic.api.entity.UserOrder;
import com.electronic.api.service.OrderService;
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
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "订单管理", description = "订单创建与查询接口")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "从购物车创建订单")
    public Result<UserOrder> createFromCart(HttpServletRequest request,
                                            @RequestBody CreateOrderRequest req) {
        Long userId = getCurrentUserId(request);
        UserOrder order = orderService.createOrderFromCart(userId, req.getCartItemIds(),
                req.getReceiverName(), req.getReceiverPhone(),
                req.getReceiverAddress(), req.getRemark());
        return Result.success(order);
    }

    @PostMapping("/direct")
    @Operation(summary = "直接购买下单")
    public Result<UserOrder> createDirect(HttpServletRequest request,
                                           @RequestBody DirectOrderRequest req) {
        Long userId = getCurrentUserId(request);
        UserOrder order = orderService.createDirectOrder(userId,
                req.getProductId(), req.getQuantity(),
                req.getReceiverName(), req.getReceiverPhone(),
                req.getReceiverAddress(), req.getRemark());
        return Result.success(order);
    }

    @GetMapping
    @Operation(summary = "获取当前用户订单列表")
    public Result<List<UserOrder>> userOrders(HttpServletRequest request) {
        Long userId = getCurrentUserId(request);
        return Result.success(orderService.getUserOrders(userId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取订单详情")
    public Result<UserOrder> detail(HttpServletRequest request, @PathVariable Long id) {
        Long userId = getCurrentUserId(request);
        return Result.success(orderService.getOrderDetail(userId, id));
    }

    @GetMapping("/all")
    @Operation(summary = "获取所有订单（管理端）")
    public Result<List<UserOrder>> allOrders() {
        return Result.success(orderService.getAllOrders());
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "更新订单状态（管理端）")
    public Result<UserOrder> updateStatus(@PathVariable Long id,
                                           @RequestBody UpdateStatusRequest req) {
        return Result.success(orderService.updateOrderStatus(id, req.getStatus()));
    }

    private Long getCurrentUserId(HttpServletRequest request) {
        User user = userService.getUserByToken(UserController.extractToken(request));
        return user.getId();
    }

    @Data
    public static class CreateOrderRequest {
        private List<Long> cartItemIds;
        private String receiverName;
        private String receiverPhone;
        private String receiverAddress;
        private String remark;
    }

    @Data
    public static class DirectOrderRequest {
        private Long productId;
        private Integer quantity;
        private String receiverName;
        private String receiverPhone;
        private String receiverAddress;
        private String remark;
    }

    @Data
    public static class UpdateStatusRequest {
        private String status;
    }
}
// AI-GENERATED-END
