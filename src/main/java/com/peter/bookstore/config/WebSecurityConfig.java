package com.peter.bookstore.config;

import com.peter.bookstore.exceptions.InternalServerException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /*
     For production use case, ensure to use real user record and role
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("clerk")
                .password(passwordEncoder().encode("password"))
                .roles("CLERK")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http){
        try{
            http.csrf(AbstractHttpConfigurer::disable);
            http.authorizeHttpRequests((auth)->{
                auth.requestMatchers(HttpMethod.GET, "/api/v1/books").permitAll();
                auth.requestMatchers(HttpMethod.GET,"/api/v1/books/search/**").permitAll();
                auth.requestMatchers(HttpMethod.POST,"/api/v1/books").hasRole("ADMIN");
                auth.requestMatchers(HttpMethod.PATCH,"/api/v1/books").hasRole("ADMIN");
                auth.requestMatchers(HttpMethod.PATCH,"/api/v1/books/{id}/update-book-status").hasRole("CLERK");
                auth.requestMatchers(HttpMethod.GET,"/api/v1/books/{id}").hasAnyRole("CLERK", "ADMIN");
                auth.requestMatchers(HttpMethod.DELETE,"/api/v1/books/{id}").hasRole("ADMIN");
            });
            http.httpBasic(withDefaults());
            return http.build();

        }catch (Exception e){
            throw new InternalServerException(e.getMessage());
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
