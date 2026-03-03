/**
 * 文件名称：ProductService.java
 * 描述：商品服务接口
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.service.impl.ProductServiceImpl
 */
package com.electronic.api.service;

import com.electronic.api.entity.Product;

import java.util.List;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
public interface ProductService {

    List<Product> findAll();

    Product findById(Long id);

    List<Product> findByCategory(String category);

    List<Product> search(String keyword);

    Product save(Product product);

    Product update(Long id, Product product);

    void deleteById(Long id);
}
// AI-GENERATED-END
