package com.tcs.jwt.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tcs.jwt.repositories.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  private UserRepository userRepository;

  public JwtUserDetailsService(
    @Autowired UserRepository userRepository
  ) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
      .findByUsername(username)
      .map(user -> new JwtUserDetails(user))
      .orElseThrow(() -> new UsernameNotFoundException("Bad Credentials"));
  }

}
