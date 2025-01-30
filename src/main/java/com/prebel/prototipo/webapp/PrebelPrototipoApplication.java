package com.prebel.prototipo.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PrebelPrototipoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrebelPrototipoApplication.class, args);
	}

	// Configuración de CORS global
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // Permite todo el path
						.allowedOrigins("http://localhost:4200", "http://localhost:3000") // URLs de frontend en desarrollo y producción
						.allowedMethods("GET", "POST", "PUT", "DELETE")
						.allowedHeaders("Authorization", "Content-Type") // Permite cabeceras necesarias
						.allowCredentials(true); // Permite cookies (si es necesario)
			}
		};
	}
}
