package com.example.sj.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web Configuration for CORS, Static Resources and MVC settings
 * 
 * Configures:
 * 1. CORS for React frontend (localhost:3000)
 * 2. Static resource handler for uploaded images (upload folder)
 * 
 * Enables cross-origin requests with proper headers and credentials
 * 
 * @author Application Team
 * @version 2.0
 */
@Configuration
public class MyWebConfig {

    @Bean
    public WebMvcConfigurer corsAndResourceConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * Configure CORS mappings
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                            "http://localhost:3000",
                            "http://127.0.0.1:3000",
                            "http://localhost:8080"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
            
            /**
             * Configure resource handlers for uploaded images
             * 
             * Access images via: http://localhost:8080/upload/filename.jpg
             * Files are stored in: project_root/upload/ folder
             */
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/upload/**")
                        .addResourceLocations("file:upload/");
                        
                System.out.println("✓ Resource handler configured for /upload/** → file:upload/");
            }
        };
    }
}
