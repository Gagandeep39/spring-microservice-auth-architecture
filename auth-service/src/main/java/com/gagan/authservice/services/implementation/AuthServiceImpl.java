package com.gagan.authservice.services.implementation;

import com.gagan.authservice.dto.LoginRequest;
import com.gagan.authservice.dto.LoginResponse;
import com.gagan.authservice.entity.User;
import com.gagan.authservice.exceptions.InvalidCredentialException;
import com.gagan.authservice.repositories.UserRepository;
import com.gagan.authservice.security.JwtProvider;
import com.gagan.authservice.services.AuthService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

  
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;


  @Override
  public LoginResponse login(LoginRequest loginRequest) {
    User user = findUserByCredentials(loginRequest.getUsername(), loginRequest.getPassword());
    return LoginResponse.builder()
      .userId(user.getUserId())
      .username(user.getUsername())
      .role(user.getRole())
      .token(jwtProvider.generateTokenWithUsername(user.getUsername()))
      .build();
  } 

  @Transactional(readOnly = true)
  public User findUserByCredentials(String username, String password) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new InvalidCredentialException("username", "User " + username + " doesn't exist"));
    if (!passwordEncoder.matches(password, user.getPassword())) throw new InvalidCredentialException("password", "Invalid Password");
    return user;
  }
  
}
