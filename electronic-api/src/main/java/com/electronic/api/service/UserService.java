/**
 * 文件名称：UserService.java
 * 描述：用户服务接口
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.service.impl.UserServiceImpl
 */
package com.electronic.api.service;

import com.electronic.api.entity.User;

import java.util.List;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
public interface UserService {

    User register(String phone, String password, String nickname);

    User login(String phone, String password);

    User getUserByToken(String token);

    User getUserById(Long id);

    List<User> findAll();

    User updateEnabled(Long id, Boolean enabled);
}
// AI-GENERATED-END
