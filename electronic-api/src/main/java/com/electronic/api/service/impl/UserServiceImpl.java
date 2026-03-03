/**
 * 文件名称：UserServiceImpl.java
 * 描述：用户服务实现类，处理注册、登录和用户管理
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.service.UserService
 */
package com.electronic.api.service.impl;

import com.electronic.api.entity.User;
import com.electronic.api.repository.UserRepository;
import com.electronic.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User register(String phone, String password, String nickname) {
        if (!phone.matches("^1[3-9]\\d{9}$")) {
            throw new RuntimeException("手机号格式不正确");
        }
        if (userRepository.existsByPhone(phone)) {
            throw new RuntimeException("该手机号已注册");
        }
        if (password == null || password.length() < 6) {
            throw new RuntimeException("密码长度不能少于6位");
        }

        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        user.setNickname(nickname != null ? nickname : "用户" + phone.substring(7));
        user.setToken(UUID.randomUUID().toString().replace("-", ""));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User login(String phone, String password) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("手机号未注册"));
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("密码错误");
        }
        if (!user.getEnabled()) {
            throw new RuntimeException("账号已被禁用");
        }
        user.setToken(UUID.randomUUID().toString().replace("-", ""));
        return userRepository.save(user);
    }

    @Override
    public User getUserByToken(String token) {
        if (token == null || token.isBlank()) {
            throw new RuntimeException("未登录");
        }
        return userRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("登录已过期，请重新登录"));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User updateEnabled(Long id, Boolean enabled) {
        User user = getUserById(id);
        user.setEnabled(enabled);
        if (!enabled) {
            user.setToken(null);
        }
        return userRepository.save(user);
    }
}
// AI-GENERATED-END
