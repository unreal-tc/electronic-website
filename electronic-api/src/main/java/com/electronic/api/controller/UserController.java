/**
 * 文件名称：UserController.java
 * 描述：用户接口控制器，提供注册、登录和用户管理功能
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.service.UserService
 */
package com.electronic.api.controller;

import com.electronic.api.common.Result;
import com.electronic.api.entity.User;
import com.electronic.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户注册、登录与管理接口")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "通过大陆手机号注册，无需验证码")
    public Result<Map<String, Object>> register(@RequestBody RegisterRequest req) {
        User user = userService.register(req.getPhone(), req.getPassword(), req.getNickname());
        return Result.success(buildUserResponse(user));
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<Map<String, Object>> login(@RequestBody LoginRequest req) {
        User user = userService.login(req.getPhone(), req.getPassword());
        return Result.success(buildUserResponse(user));
    }

    @GetMapping("/info")
    @Operation(summary = "获取当前用户信息")
    public Result<Map<String, Object>> info(HttpServletRequest request) {
        User user = userService.getUserByToken(extractToken(request));
        return Result.success(buildUserResponse(user));
    }

    @GetMapping
    @Operation(summary = "获取所有用户列表（管理端）")
    public Result<List<User>> list() {
        return Result.success(userService.findAll());
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "启用/禁用用户（管理端）")
    public Result<User> updateStatus(@PathVariable Long id,
                                     @RequestBody StatusRequest req) {
        return Result.success(userService.updateEnabled(id, req.getEnabled()));
    }

    private Map<String, Object> buildUserResponse(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("phone", user.getPhone());
        map.put("nickname", user.getNickname());
        map.put("avatar", user.getAvatar());
        map.put("token", user.getToken());
        map.put("enabled", user.getEnabled());
        map.put("createdAt", user.getCreatedAt());
        return map;
    }

    static String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return header;
    }

    @Data
    public static class RegisterRequest {
        private String phone;
        private String password;
        private String nickname;
    }

    @Data
    public static class LoginRequest {
        private String phone;
        private String password;
    }

    @Data
    public static class StatusRequest {
        private Boolean enabled;
    }
}
// AI-GENERATED-END
