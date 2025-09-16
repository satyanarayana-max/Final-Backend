package com.example.college.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(List.of("http://localhost:5173"));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(List.of("*"));
                config.setAllowCredentials(true);
                return config;
            }))
//            .authorizeHttpRequests(auth -> auth
//                // Public endpoints
//                .requestMatchers("/api/auth/**").permitAll()
//                .requestMatchers("/api/compiler/**").permitAll()
//                .requestMatchers("/uploads/**").permitAll()
            
            .authorizeHttpRequests(auth -> auth
            	    .requestMatchers("/uploads/**").permitAll()
            	    .requestMatchers("/static/**", "/public/**", "/resources/**", "/webjars/**").permitAll()
            	    .requestMatchers("/api/auth/**").permitAll()
            	    .requestMatchers("/api/compiler/**").permitAll()
            	    .requestMatchers("/api/leaderboard/**").permitAll()
            	    
            	
            	    .requestMatchers(HttpMethod.POST, "/api/coding/questions").hasAuthority("ROLE_TEACHER")


                // Courses
                .requestMatchers(HttpMethod.GET, "/api/courses/**").hasAnyAuthority("ROLE_STUDENT", "ROLE_TEACHER", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/courses/**").hasAnyAuthority("ROLE_TEACHER", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/courses/**").hasAnyAuthority("ROLE_TEACHER", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/courses/**").hasAnyAuthority("ROLE_TEACHER", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/{courseId}/enroll/**").hasAuthority("ROLE_STUDENT")
                .requestMatchers(HttpMethod.GET, "/api/{courseId}/isEnrolled/**").hasAuthority("ROLE_STUDENT")

                // Quizzes
                .requestMatchers(HttpMethod.GET, "/api/quiz/**").hasAnyAuthority("ROLE_STUDENT", "ROLE_TEACHER", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/quiz/{id}/attempt**").hasAnyAuthority("ROLE_STUDENT", "ROLE_TEACHER", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/quiz").hasAnyAuthority("ROLE_TEACHER", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/quiz/**").hasAnyAuthority("ROLE_TEACHER", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/quiz/**").hasAnyAuthority("ROLE_TEACHER", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/quiz/submit").hasAuthority("ROLE_STUDENT") // Fixed

                // Videos
                .requestMatchers(HttpMethod.GET, "/api/videos/**").hasAnyAuthority("ROLE_STUDENT", "ROLE_TEACHER", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/videos/**").hasAnyAuthority("ROLE_TEACHER", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/videos/**").hasAnyAuthority("ROLE_TEACHER", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/videos/**").hasAnyAuthority("ROLE_TEACHER", "ROLE_ADMIN")

                // Admin
                .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")

                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
