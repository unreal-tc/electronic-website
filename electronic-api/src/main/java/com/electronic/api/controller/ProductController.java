/**
 * 文件名称：ProductController.java
 * 描述：商品管理接口控制器
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.service.ProductService
 */
package com.electronic.api.controller;

import com.electronic.api.common.Result;
import com.electronic.api.entity.Product;
import com.electronic.api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "商品管理", description = "商品的增删改查接口")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "查询所有上架商品")
    public Result<List<Product>> list() {
        return Result.success(productService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询商品")
    public Result<Product> getById(
            @Parameter(description = "商品ID") @PathVariable Long id) {
        return Result.success(productService.findById(id));
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "根据分类查询商品")
    public Result<List<Product>> getByCategory(
            @Parameter(description = "商品分类") @PathVariable String category) {
        return Result.success(productService.findByCategory(category));
    }

    @GetMapping("/search")
    @Operation(summary = "搜索商品")
    public Result<List<Product>> search(
            @Parameter(description = "搜索关键字") @RequestParam String keyword) {
        return Result.success(productService.search(keyword));
    }

    @PostMapping
    @Operation(summary = "新增商品")
    public Result<Product> create(@RequestBody Product product) {
        return Result.success(productService.save(product));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新商品")
    public Result<Product> update(
            @Parameter(description = "商品ID") @PathVariable Long id,
            @RequestBody Product product) {
        return Result.success(productService.update(id, product));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除商品")
    public Result<Void> delete(
            @Parameter(description = "商品ID") @PathVariable Long id) {
        productService.deleteById(id);
        return Result.success();
    }
}
// AI-GENERATED-END
