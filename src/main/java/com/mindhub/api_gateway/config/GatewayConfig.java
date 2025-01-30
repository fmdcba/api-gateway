package com.mindhub.api_gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public RouteLocator customRouter(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes()
                .route("ms-user", r -> r.path("/api/users/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://ms-user"))
                .route("ms-user", r -> r.path("/api/auth/**")
                        .uri("lb://ms-user"))
                .route("ms-product", r -> r.path("/api/products/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://ms-product"))
                .route("ms-order", r -> r.path("/api/orders/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://ms-order"))
                .build();
    }
}
