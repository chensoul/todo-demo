package com.example.todo.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI todoOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("TODO 后端 API")
                        .description("基于 Spring Boot 的 TODO 后端服务，支持国际化、统一响应、软删除、分页、排序、模糊搜索等功能。")
                        .version("v1.0.0")
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("项目文档")
                        .url("https://github.com/your-org/your-repo"));
    }
}
