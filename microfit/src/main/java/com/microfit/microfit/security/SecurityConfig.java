package com.microfit.microfit.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // Auth APIs
                        .requestMatchers("/api/auth/**")
                        .permitAll()

                        // User APIs
                        .requestMatchers("/api/users/**")
                        .permitAll()

                        // Workout APIs
                        .requestMatchers("/api/workouts/**")
                        .permitAll()

                        // Frontend Files
                        .requestMatchers(
                                "/",
                                "/dashboard.html",
                                "/login.html",
                                "/style.css",
                                "/app.js",
                                "/images/**"
                        )
                        .permitAll()

                        .anyRequest()
                        .permitAll()
                )

                .formLogin(form -> form.disable())

                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }
}
