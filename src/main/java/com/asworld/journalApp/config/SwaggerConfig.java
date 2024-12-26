package com.asworld.journalApp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Journal App API")
                        .description("API Documentation for Journal App")
                        .version("1.0.0")
                )
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Server 1"),
                        new Server().url("http://localhost:8081").description("Local Server 2")
                ))
                .tags(List.of(
                        new Tag().name("Public APIs"),
                        new Tag().name("User APIs"),
                        new Tag().name("Journal APIs"),
                        new Tag().name("Admin APIs")
                ))
                // Apply Security Globally (Requires Token for All Endpoints by Default)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                // Add Security Configuration
                .components(new Components()
                .addSecuritySchemes(
                        "bearerAuth", new SecurityScheme()  // Add JWT Bearer Token Security
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization")
                ));
    }
}
