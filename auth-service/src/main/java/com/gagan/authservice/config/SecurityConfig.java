/**
 * @author Gagandeep Singh
 * @email singh.gagandeep3911@gmail.com
 * @create date 2021-01-09 05:47:48
 * @modify date 2021-01-09 05:47:48
 * @desc [description]
 */
package com.gagan.authservice.config;

import com.gagan.authservice.security.CustomAuthenticationEntryPoint;
import com.gagan.authservice.security.JwtAuthenticationFilter;
import com.gagan.authservice.security.JwtAuthorizationFilter;
import com.gagan.authservice.security.JwtProvider;
import com.gagan.authservice.services.AuthService;
import com.gagan.authservice.services.implementation.JwtUserDetailsServiceImpl;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
  
  private JwtUserDetailsServiceImpl userDetailsService;
  private BCryptPasswordEncoder passwordEncoder;
  private JwtProvider jwtProvider;
  private AuthService authService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      // Required when not using gateway
      // .cors().and()
      .csrf().disable()
      .authorizeRequests().antMatchers("/auth/**" , "/h2/**", "/swagger*/**", "/v2/api-docs").permitAll()
      .antMatchers().permitAll()
      .anyRequest().authenticated()
      .and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
    // Works but need to make 2 calls
    // http.addFilter(new JwtAuthenticationFilter(authenticationManager(), authService));
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
