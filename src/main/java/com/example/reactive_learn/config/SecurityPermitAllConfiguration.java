package com.example.reactive_learn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@Configuration
@EnableWebFluxSecurity
public class SecurityPermitAllConfiguration {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf().disable()
                .authorizeExchange().anyExchange().permitAll()
                .and().httpBasic().and().formLogin();
//        http.csrf().disable()
//                .securityMatcher(ServerWebExchangeMatchers.pathMatchers("/actuator"))
//                .authorizeExchange()
//                .anyExchange().authenticated();
        return http.build();

    }

}
