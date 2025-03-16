// package com.truongle.gateway.configs;
//
// import org.springframework.cloud.gateway.route.RouteLocator;
// import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// @Configuration
// public class GatewayConfig {
//     @Bean
//     public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//         return builder.routes()
//             .route("service_route", r -> r.path("/api/**")
//                 .filters(f -> f
//                     .requestHeaderToRequestUri("Authorization")  // Log auth header for debugging
//                     .addResponseHeader("Access-Control-Allow-Origin", "*")
//                     .addResponseHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
//                     .addResponseHeader("Access-Control-Allow-Headers", "Authorization, Content-Type")
//                 )
//                 .uri("lb://your-service"))
//             .build();
//     }
// }
