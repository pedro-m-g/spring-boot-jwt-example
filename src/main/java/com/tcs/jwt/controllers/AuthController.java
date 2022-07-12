package com.tcs.jwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.jwt.entities.User;
import com.tcs.jwt.repositories.UserRepository;
import com.tcs.jwt.request.SignUpRequest;
import com.tcs.jwt.responses.SignUpResponse;

@RestController
public class AuthController {

  private PasswordEncoder passwordEncoder;
  private UserRepository userRepository;

  public AuthController(
      @Autowired PasswordEncoder passwordEncoder,
      @Autowired UserRepository userRepository
  ) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  @PostMapping("signup")
  public SignUpResponse signUp(@RequestBody SignUpRequest request) {
    User user = new User(
      request.getUsername(),
      passwordEncoder.encode(request.getPassword())
    );
    userRepository.save(user);
    return new SignUpResponse(request.getUsername());
  }

}
