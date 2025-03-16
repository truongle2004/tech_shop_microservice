// package com.truongle.gateway.configs;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
// import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
//
// @Configuration
// public class JwtDecoderConfig {
//
//     @Bean
//     public ReactiveJwtDecoder jwtDecoder() {
//         return NimbusReactiveJwtDecoder
//                 .withJwkSetUri("http://localhost:8088/realms/tech_shop_auth_api/protocol/openid-connect/certs")
//                 .build();
//     }
// }
