package com.ajou.hertz.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi(@Value("${hertz.app-version}") String appVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title("Hertz Server API Docs")
                        .description("Hertz API 명세서")
                        .version(appVersion))
                .externalDocs(new ExternalDocumentation()
                        .description("Github organization of hertz")
                        .url("https://github.com/Ajou-Hertz"))
                .components(new Components().addSecuritySchemes(
                        "access-token",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("Bearer")
                                .bearerFormat("JWT")
                ));
    }

    @Bean
    public GroupedOpenApi groupedOpenApiVersion1() {
        return GroupedOpenApi.builder()
                .group("v1")
                .packagesToScan("com.ajou.hertz")
                .pathsToMatch("/v1/**")
                .build();
    }
}

