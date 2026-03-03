/**
 * 文件名称：Knife4jConfig.java
 * 描述：Knife4j/OpenAPI 接口文档配置
 *
 * @author Cursor Agent and ZKT AI 编程助手，GENERATED-BY-Claude
 * @since 2026-03-03
 * @see com.electronic.api.controller
 */
package com.electronic.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// AI-GENERATED-BEGIN
// GENERATED-BY-Claude
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("电商网站 API")
                        .description("Electronic Website RESTful API 接口文档")
                        .version("0.0.1")
                        .contact(new Contact()
                                .name("Electronic Team")));
    }
}
// AI-GENERATED-END
