package com.example.sj.configuration;

import com.example.sj.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security Configuration
 * 
 * Configures JWT-based stateless authentication for REST API
 * - Disables CSRF (appropriate for stateless APIs)
 * - Sets up session management as STATELESS (no server-side sessions)
 * - Defines public and protected endpoints
 * - Adds JWT authentication filter for token validation
 * - Configures password encoding with BCrypt (for registration)
 * 
 * @author Application Team
 * @version 2.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Security Filter Chain Configuration
     * Configures HTTP security, JWT filter integration, and endpoint authorization
     * 
     * @param http HttpSecurity to configure
     * @return SecurityFilterChain with configured security rules
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/roles").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/roles").permitAll()
                        .requestMatchers("/api/categories").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                        .anyRequest().authenticated()
                )
                
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                
                .exceptionHandling(exception -> exception
                    .authenticationEntryPoint((request, response, authException) -> {
                        response.setContentType("application/json");
                        response.setStatus(401);
                        response.getWriter().write("{\"error\": \"Unauthorized - Valid JWT token required\"}");
                    })
                    .accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setContentType("application/json");
                        response.setStatus(403);
                        response.getWriter().write("{\"error\": \"Access Denied - Insufficient permissions\"}");
                    })
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
