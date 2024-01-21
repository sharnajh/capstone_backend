package com.yearup.capstone.config;

import com.yearup.capstone.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            // Add both your local and Netlify domains
                            config.setAllowedOrigins(List.of(
                                    "http://localhost:3000", // Local frontend origin
                                    "https://whimsical-caramel-b06aa5.netlify.app", // Netlify domain
                                    "http://capstonereactfrontend.s3-website-us-east-1.amazonaws.com"
                            ));
                            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                            config.setAllowedHeaders(List.of("*"));
                            config.setAllowCredentials(true);
                            return config;
                        })
                )
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll()
                );

        return http.build();
    }


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors
//                        .configurationSource(request -> {
//                            CorsConfiguration config = new CorsConfiguration();
//                            config.setAllowedOrigins(List.of("http://localhost:3000")); // Frontend origin
//                            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
//                            config.setAllowedHeaders(List.of("*"));
//                            config.setAllowCredentials(true);
//                            return config;
//                        })
//                )
//                .sessionManagement(session -> {
//                    session.sessionFixation().none();
//                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
//                    session.sessionAuthenticationStrategy(sessionAuthenticationStrategy());
//                })
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/auth/register", "/auth/login", "/auth/logout").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .csrf(csrf -> csrf.disable());
//
//        return http.build();
//    }

    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new SessionFixationProtectionStrategy();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}

