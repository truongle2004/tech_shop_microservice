// package com.truongle.gateway.configs;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
// import org.springframework.security.config.web.server.ServerHttpSecurity;
// import org.springframework.security.web.server.SecurityWebFilterChain;
//
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.truongle.gateway.exceptions.AccessDeniedExceptionHandler;
// import com.truongle.gateway.exceptions.AuthenticationExceptionHandler;
//
// import lombok.RequiredArgsConstructor;
//
// import java.util.Arrays;
// import java.util.Collections;
//
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.oauth2.jwt.JwtDecoder;
// import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.reactive.CorsConfigurationSource;
// import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//
// @Configuration
// @EnableWebFluxSecurity
// @RequiredArgsConstructor
// public class SecurityConfig {
//
//     // private final ObjectMapper objectMapper;
//     //
//     // @Bean
//     // public AuthenticationExceptionHandler authenticationExceptionHandler() {
//     // return new AuthenticationExceptionHandler(objectMapper);
//     // }
//     //
//     // @Bean
//     // public AccessDeniedExceptionHandler accessDeniedExceptionHandler() {
//     // return new AccessDeniedExceptionHandler(objectMapper);
//     // }
//
//     // @Bean
//     // public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity
//     // httpSecurity)
//     // throws Exception {
//     // return httpSecurity
//     // .csrf(ServerHttpSecurity.CsrfSpec::disable)
//     // .authorizeExchange(auth -> auth
//     // .pathMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
//     // .pathMatchers(HttpMethod.POST, "/api/v1/carts/**")
//     // .hasAuthority("ROLE_USER")
//     // .anyExchange().authenticated())
//     // // .oauth2ResourceServer(oauth2 -> oauth2
//     // // .authenticationEntryPoint(authenticationExceptionHandler())
//     // // .accessDeniedHandler(accessDeniedExceptionHandler())
//     // // .jwt(Customizer.withDefaults()))
//     // .build();
//     //
//     // }
//
//     @Bean
//     public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//         http
//                 .authorizeExchange(exchanges -> exchanges.anyExchange().permitAll())
//                 .csrf(csrf -> csrf.disable());
//
//
//         return http.build();
//     }
//
//     @Bean
//     CorsConfigurationSource corsConfiguration() {
//         CorsConfiguration corsConfig = new CorsConfiguration();
//         corsConfig.applyPermitDefaultValues();
//         corsConfig.setAllowedOrigins(Collections.singletonList("*"));
//         corsConfig.setAllowedMethods(Collections.singletonList("*"));
//         corsConfig.setAllowedHeaders(Collections.singletonList("*"));
//
//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", corsConfig);
//         return source;
//     }
// }
