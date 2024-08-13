package com.ajlearnings.workbuddy.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Workbuddy")
                        .version("v1.0")
                        .description("These are the apis of workbuddy application.")
                        .contact(new Contact()
                                .name("aj-learnings")
                                .email("aayushjalan.learning@gmail.com")));
    }
}

