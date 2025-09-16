package com.example.college.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.PostConstruct;

@Configuration
public class WebConfig {
	
	
	private final String basePath = System.getProperty("user.dir") + "/uploads/";

    @PostConstruct
    public void logUploadPath() {
        System.out.println("✅ Serving static files from: " + basePath);
    }


    @Bean
    public WebMvcConfigurer corsAndResourceConfigurer() {
        return new WebMvcConfigurer() {

            // ✅ CORS configuration
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:5173") // React frontend port
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }

            // ✅ Serve uploaded files
//            @Override
//            public void addResourceHandlers(ResourceHandlerRegistry registry) {
//                registry.addResourceHandler("/uploads/**")
//                        .addResourceLocations("file:uploads/")
//                        .addResourceLocations("file:uploads/videos/");
//            }
//            
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
//                String basePath = System.getProperty("user.dir") + "/uploads/";
                System.out.println("Serving static files from: " + basePath);

                registry.addResourceHandler("/uploads/**")
                        .addResourceLocations("file:" + basePath + "/");
            }


            
            
            

        };
    }
}
