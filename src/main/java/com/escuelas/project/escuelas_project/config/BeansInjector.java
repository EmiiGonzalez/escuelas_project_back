package com.escuelas.project.escuelas_project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class BeansInjector implements WebMvcConfigurer {

    @Value("${CORS_ALLOWED_ORIGINS}")
    private String corsAllowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = corsAllowedOrigins.split(",");

        registry.addMapping("/**") // Permite cualquier ruta
                .allowedOrigins(origins) // Orígenes específicos permitidos
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
