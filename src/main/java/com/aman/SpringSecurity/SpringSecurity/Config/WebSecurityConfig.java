package com.aman.SpringSecurity.SpringSecurity.Config;

import com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Permissions;
import com.aman.SpringSecurity.SpringSecurity.Filters.JwtAuthFilter;
import com.aman.SpringSecurity.SpringSecurity.Filters.LoggingFilter;
import com.aman.SpringSecurity.SpringSecurity.Handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Permissions.*;
import static com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Role.ADMIN;
import static com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Role.CREATOR;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {


    private final JwtAuthFilter jwtAuthFilter;
    private final LoggingFilter loggingFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private static final String[] publicRoutes = {
            "/error", "/auth/**", "/home.html","/subscription/**"
    };
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{
        httpSecurity.authorizeHttpRequests(auth -> auth
                .requestMatchers(publicRoutes).permitAll()
                .requestMatchers(HttpMethod.GET, "/post/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/post/**").hasAnyRole(ADMIN.name(), CREATOR.name())
                .requestMatchers(HttpMethod.POST,"/post/**").hasAuthority(POST_CREATE.name())
                .requestMatchers(HttpMethod.PUT,"/post/**").hasAuthority(POST_UPDATE.name()))
                .csrf(csrfConfig -> csrfConfig.disable())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(loggingFilter, JwtAuthFilter.class)
                .oauth2Login(oauth2Config -> oauth2Config
                        .failureUrl("/login?error=true")
                        .successHandler(oAuth2SuccessHandler)

                );
        return httpSecurity.build();

    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
