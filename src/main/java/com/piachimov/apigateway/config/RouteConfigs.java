package com.piachimov.apigateway.config;

import com.piachimov.apigateway.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RouteConfigs {

    private final AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/customer/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://customer-reactive"))
                .route(p -> p
                        .path("/api/user/**")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("lb://authentication-server"))
                .route(p -> p
                        .path("/api/router/repository/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://customer-reactive"))
        .build();
    }
}
