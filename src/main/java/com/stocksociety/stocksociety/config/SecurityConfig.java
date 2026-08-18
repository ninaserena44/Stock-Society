package com.stocksociety.stocksociety.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/about",
                                "/services",
                                "/login",
                                "/register",
                                "/access-denied",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/h2-console/**"
                        ).permitAll()

                        // ADMIN only
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        // Create item form
                        .requestMatchers(HttpMethod.GET, "/items/new")
                        .hasAnyRole("STAFF", "ADMIN")

                        // Create item
                        .requestMatchers(HttpMethod.POST, "/items")
                        .hasAnyRole("STAFF", "ADMIN")

                        // Edit item form
                        .requestMatchers(HttpMethod.GET, "/items/*/edit")
                        .hasAnyRole("STAFF", "ADMIN")

                        // Delete MUST come before general POST /items/*
                        .requestMatchers(HttpMethod.POST, "/items/*/delete")
                        .hasRole("ADMIN")

                        // Update item
                        .requestMatchers(HttpMethod.POST, "/items/*")
                        .hasAnyRole("STAFF", "ADMIN")

                        // View items/details
                        .requestMatchers(HttpMethod.GET, "/items/**")
                        .authenticated()

                        .anyRequest()
                        .authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                                response.sendRedirect("/access-denied");
                        })
                )

                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )

                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                );

        return http.build();
    }
}