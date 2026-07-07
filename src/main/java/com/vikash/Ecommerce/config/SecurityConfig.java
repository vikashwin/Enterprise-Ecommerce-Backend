package com.vikash.Ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.vikash.Ecommerce.entity.type.UserRole.ADMIN;

@Configuration // That denote the security configuration file
@EnableWebSecurity // Disable the default web security chain
public class SecurityConfig {

    @Bean // Use for create bean of any method
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults()) //Allow user to send request with postman by username and password
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**","/auth/**").permitAll()
                        .requestMatchers("/admin/**").hasRole(ADMIN.name())
                        .anyRequest().authenticated()
                ).build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //Convert plain text into hash but not vise versa
    }

}
