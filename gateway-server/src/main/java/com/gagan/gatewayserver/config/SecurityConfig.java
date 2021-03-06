package com.gagan.gatewayserver.config;

import com.gagan.gatewayserver.security.CustomAuthenticationEntryPoint;
import com.gagan.gatewayserver.security.JwtAuthorizationFilter;
import com.gagan.gatewayserver.security.JwtProvider;
import com.gagan.gatewayserver.services.implementation.JwtUserDetailsServiceImpl;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
  
  private JwtUserDetailsServiceImpl userDetailsService;
  private BCryptPasswordEncoder passwordEncoder;
  private JwtProvider jwtProvider;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      // Makes header as default with origin as *
      .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()).and() // Required for accessing prpotected routes
      .csrf().disable()
      .authorizeRequests().antMatchers("/auth-service/**", "/actuator/**", "/**/h2/**", "/**/swagger*/**", "/**/v2/api-docs").permitAll()
      .anyRequest().authenticated()
      .and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
    http.addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtProvider, userDetailsService));
    http.headers().frameOptions().disable();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  public AuthenticationEntryPoint authenticationEntryPoint() {
    return new CustomAuthenticationEntryPoint();
  }
}
