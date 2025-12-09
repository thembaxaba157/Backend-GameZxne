package com.game.gamezxne.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.game.gamezxne.auth.jwt.JwtAuthenticationFilter;

import jakarta.servlet.DispatcherType;

// Configures authentication and security, like JWT, roles
@Configuration
public class SecurityConfig {
    
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter){
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf .disable())
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()  //allows the spring boot validator to return proper error messages instead of 403 forbidden
                    .requestMatchers("/auth/**").permitAll() // Allow public access to /auth
                    .requestMatchers("/ws/**").permitAll() // Allow public access to /game
                    .requestMatchers("/lobby").permitAll()
                    .anyRequest().authenticated()         // All other requests need authentication
            )
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless sessions (JWT)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JWT filter

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


      @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
