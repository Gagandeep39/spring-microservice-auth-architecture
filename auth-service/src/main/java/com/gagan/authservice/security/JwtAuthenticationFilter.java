/**
 * @author Gagandeep Singh
 * @email singh.gagandeep3911@gmail.com
 * @create date 2021-01-09 07:38:35
 * @modify date 2021-01-09 07:38:35
 * @desc [description]
 */
package com.gagan.authservice.security;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gagan.authservice.dto.LoginResponse;
import com.gagan.authservice.entity.User;
import com.gagan.authservice.repositories.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;
  private JwtProvider jwtProvider;
  private UserRepository userRepository;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserRepository userRepository) {
    this.authenticationManager = authenticationManager;
    this.jwtProvider = jwtProvider;
    this.userRepository = userRepository;
    setFilterProcessesUrl("/auth/login");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
    try {
      User creds = new ObjectMapper().readValue(request.getInputStream(), User.class);
      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), new ArrayList<>()));
    } catch (Exception e) {
      log.debug(e.getMessage());
    }
    return super.attemptAuthentication(request, response);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
      Authentication authResult) throws IOException, ServletException {
    response.setContentType("application/json");
    ObjectMapper mapper = new ObjectMapper();
    OutputStream out = response.getOutputStream();
    mapper.writeValue(out, buildResponse(authResult));
  }

  private LoginResponse buildResponse(Authentication auth) {
    try {
      User user = userRepository.findByUsername(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
          .orElseThrow(() -> new RuntimeException());
      return LoginResponse.builder()
        .userId(user.getUserId())
        .username(user.getUsername())
        .role(user.getRole())
        .token(jwtProvider.generateTokenWithUsername(user.getUsername()))
        .build();
    } catch (Exception e) {
      log.info(e.getMessage());
    }
    return null;
  }

}
