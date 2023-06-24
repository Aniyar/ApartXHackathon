package com.hackathon.apartxhackathon.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.hackathon.apartxhackathon.user.Permission.*;
import static com.hackathon.apartxhackathon.user.Role.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .cors()
            .and()
            .csrf()
            .disable()
            .authorizeRequests()
            .requestMatchers("/api/v1/cleaner/**").hasAnyRole(ADMIN.name(), CLEANER.name())
            .requestMatchers(GET, "/api/v1/cleaner/**").hasAnyAuthority(ADMIN_READ.name(), CLEANER_READ.name())
            .requestMatchers(POST, "/api/v1/cleaner/**").hasAnyAuthority(ADMIN_CREATE.name(), CLEANER_CREATE.name())
            .requestMatchers(PUT, "/api/v1/cleaner/**").hasAnyAuthority(ADMIN_UPDATE.name(), CLEANER_UPDATE.name())
            .requestMatchers(DELETE, "/api/v1/cleaner/**").hasAnyAuthority(ADMIN_DELETE.name(), CLEANER_DELETE.name())
            .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
            .requestMatchers(GET, "/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
            .requestMatchers(POST, "/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
            .requestMatchers(PUT, "/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
            .requestMatchers(DELETE, "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())
            .requestMatchers("/api/v1/user/**").hasAnyRole(USER.name(), ADMIN.name(), LANDLORD.name(), CLEANER.name())

            .requestMatchers(
                    "/api/v1/home/**",
                    "/api/v1/auth/**",
                    "/v2/api-docs",
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/swagger-resources",
                    "/swagger-resources/**",
                    "/configuration/ui",
                    "/configuration/security",
                    "/swagger-ui/**",
                    "/webjars/**",
                    "/swagger-ui.html"
            )
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .logout()
            .logoutUrl("/api/v1/auth/logout")
            .addLogoutHandler(logoutHandler)
            .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
    ;

    return http.build();
  }
}




