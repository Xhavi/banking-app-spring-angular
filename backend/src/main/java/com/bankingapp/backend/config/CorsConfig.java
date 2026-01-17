package com.bankingapp.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  private final String[] allowedOrigins;

  public CorsConfig(
    @Value("${app.cors.allowed-origins:http://localhost:4200}") String allowedOrigins
  ) {
    this.allowedOrigins = allowedOrigins.split(",");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins(trimAll(allowedOrigins))
      .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
      .allowedHeaders("*")
      .maxAge(3600);
  }

  private static String[] trimAll(String[] values) {
    for (int i = 0; i < values.length; i++) {
      values[i] = values[i].trim();
    }
    return values;
  }
}
