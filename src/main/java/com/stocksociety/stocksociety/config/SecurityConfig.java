package com.stocksociety.stocksociety.config;

import com.stocksociety.stocksociety.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
    public DaoAuthenticationProvider authenticationProvider(
            UserService userService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider authenticationProvider
    ) throws Exception {

        http
                .authenticationProvider(authenticationProvider)

                .authorizeHttpRequests(auth -> auth

                        // Public pages
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

                        // Admin-only area
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        // Create item: STAFF or ADMIN
                        .requestMatchers("/items/new")
                        .hasAnyRole("STAFF", "ADMIN")

                        // POST /items creates a new item
                        .requestMatchers(
                                org.springframework.http.HttpMethod.POST,
                                "/items"
                        )
                        .hasAnyRole("STAFF", "ADMIN")

                        // Edit form: STAFF or ADMIN
                        .requestMatchers("/items/*/edit")
                        .hasAnyRole("STAFF", "ADMIN")

                        // Delete item: ADMIN only
                        .requestMatchers(
                                org.springframework.http.HttpMethod.POST,
                                "/items/*/delete"
                        )
                        .hasRole("ADMIN")

                        // Update item: STAFF or ADMIN
                        .requestMatchers(
                                org.springframework.http.HttpMethod.POST,
                                "/items/*"
                        )
                        .hasAnyRole("STAFF", "ADMIN")

                        // Item list and details require login
                        .requestMatchers("/items/**")
                        .authenticated()

                        // Everything else requires login
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
                        .accessDeniedPage("/access-denied")
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