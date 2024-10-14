package com.appcenter.marketplace.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Value("${swagger.server-url}")
    private String serverUrl;

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        server.setUrl(serverUrl);
        return new OpenAPI()
                .components(new Components())
                .addServersItem(server)
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("MarketPlace API")
                .description("MarketPlace API 설명서")
                .version("1.0.0");
    }
}
