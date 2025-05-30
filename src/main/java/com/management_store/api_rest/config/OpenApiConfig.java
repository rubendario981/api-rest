package com.management_store.api_rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@SecurityScheme(
    name = "BearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Management Store API")
                .version("1.0.0")
                .description("API documentation for the Management Store application.")
                .termsOfService("http://swagger.io/terms/")
                .license(new License()
                .name("Apache 2.0")
                .url("http://springdoc.org")));
    }
}
