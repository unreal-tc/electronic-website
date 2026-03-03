/**
 * 文件名称：UserRepository.java
 * 描述：用户数据访问层
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.entity.User
 */
package com.electronic.api.repository;

import com.electronic.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhone(String phone);

    Optional<User> findByToken(String token);

    boolean existsByPhone(String phone);
}
// AI-GENERATED-END
