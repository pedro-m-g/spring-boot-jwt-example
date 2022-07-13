package com.tcs.jwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.jwt.entities.User;
import com.tcs.jwt.repositories.UserRepository;
import com.tcs.jwt.request.LoginRequest;
import com.tcs.jwt.request.SignUpRequest;
import com.tcs.jwt.responses.LoginResponse;
import com.tcs.jwt.responses.SignUpResponse;
import com.tcs.jwt.services.auth.TokenGenerator;

@RestController
@CrossOrigin
public class AuthController {

  private PasswordEncoder passwordEncoder;
  private UserRepository userRepository;
  private AuthenticationManager authenticationManager;
  private TokenGenerator tokenGenerator;

  public AuthController(
      @Autowired PasswordEncoder passwordEncoder,
      @Autowired UserRepository userRepository,
      @Autowired AuthenticationManager authenticationManager,
      @Autowired TokenGenerator tokenGenerator
  ) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.tokenGenerator = tokenGenerator;
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

  @PostMapping("login")
  public LoginResponse login(@RequestBody LoginRequest request) {
    UsernamePasswordAuthenticationToken auth =
      new UsernamePasswordAuthenticationToken(
        request.getUsername(),
        request.getPassword()
    );
    authenticationManager.authenticate(auth);
    String token = tokenGenerator.createToken(request.getUsername());
    return new LoginResponse(token);
  }

  @GetMapping("hello")
  public String hello() {
    return "Hello world";
  }

}
