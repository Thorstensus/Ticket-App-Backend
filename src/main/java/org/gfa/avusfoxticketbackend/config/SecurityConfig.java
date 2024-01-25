package org.gfa.avusfoxticketbackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final CustomJwtAuthenticationEntryPoint customJwtAuthenticationEntryPoint;

  @Autowired
  public SecurityConfig(
      JwtAuthenticationFilter jwtAuthFilter,
      AuthenticationProvider authenticationProvider,
      CustomJwtAuthenticationEntryPoint customJwtAuthenticationEntryPoint) {
    this.jwtAuthFilter = jwtAuthFilter;
    this.authenticationProvider = authenticationProvider;
    this.customJwtAuthenticationEntryPoint = customJwtAuthenticationEntryPoint;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers(
                        "/api/news/**",
                        "/api/users",
                        "/api/users/login",
                        "/api/email-verification/**",
                        "/api/refresh-token")
                    .permitAll()
                    .requestMatchers("/api/admin//**")
                    .hasRole("ADMIN")
                    .anyRequest()
                    .authenticated())
        .sessionManagement(
            sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(
            exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(customJwtAuthenticationEntryPoint));

    return http.build();
  }
}
