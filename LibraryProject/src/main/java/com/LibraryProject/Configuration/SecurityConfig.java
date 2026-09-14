package com.LibraryProject.Configuration;

import com.LibraryProject.Security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                // JWT ke saath CSRF ki zarurat nahi

                .csrf(csrf -> csrf.disable())

                // Session use nahi karni
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Signup & Login public
                        // Baaki APIs authenticated
                        .requestMatchers(HttpMethod.POST, "/auth/api/signup").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/api/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/api/refresh").permitAll()
                        .anyRequest().authenticated()
                )

                // JWT filter ko Spring Security filter chain me add karo
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );


        return http.build();
    }
}