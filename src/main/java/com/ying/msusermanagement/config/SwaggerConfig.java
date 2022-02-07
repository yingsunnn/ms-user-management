package com.ying.msusermanagement.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("User Management Microservice")
//            .version(appVersion + ":" + commitId.replaceAll("[^a-zA-Z0-9]", ""))
//            .description(appDescription + ", Environment:[" + profile + "]")
//            .termsOfService("http://swagger.io/terms/")
//            .license(new License().name("Apache 2.0").url("http://springdoc.org")
        )
        .components(new Components()
            .addSecuritySchemes("bearer-key",
                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
  }
}
