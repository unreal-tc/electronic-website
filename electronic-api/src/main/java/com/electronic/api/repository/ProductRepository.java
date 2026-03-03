/**
 * 文件名称：ProductRepository.java
 * 描述：商品数据访问层
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.entity.Product
 */
package com.electronic.api.repository;

import com.electronic.api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);

    List<Product> findByEnabledTrue();

    List<Product> findByNameContaining(String name);
}
// AI-GENERATED-END
