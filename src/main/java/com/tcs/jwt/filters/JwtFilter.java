package com.tcs.jwt.filters;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tcs.jwt.services.auth.TokenGenerator;

@Component
public class JwtFilter extends OncePerRequestFilter {

  private TokenGenerator tokenGenerator;

  public JwtFilter(
    @Autowired TokenGenerator tokenGenerator
  ) {
    this.tokenGenerator = tokenGenerator;
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    String token = request.getHeader("Authorization");
    if (token == null || token.isBlank()) {
      filterChain.doFilter(request, response);
      return;
    }
    String username = tokenGenerator.getUsername(token);
    if (username == null) {
      filterChain.doFilter(request, response);
      return;
    }
    UsernamePasswordAuthenticationToken auth =
      new UsernamePasswordAuthenticationToken(
        username,
        token,
        Arrays.asList(new SimpleGrantedAuthority("USER"))
      );
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

}
