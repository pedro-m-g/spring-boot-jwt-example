package com.tcs.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tcs.jwt.filters.JwtFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private UserDetailsService userDetailsService;
  private JwtFilter jwtFilter;

  public WebSecurityConfig(
    @Autowired UserDetailsService userDetailsService,
    @Autowired JwtFilter jwtFilter
  ) {
    this.userDetailsService = userDetailsService;
    this.jwtFilter = jwtFilter;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
        .antMatchers("/login").permitAll()
        .antMatchers("/signup").permitAll()
        .anyRequest().authenticated()
        .and()
      .csrf().disable()
      .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
      .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  public void configure(AuthenticationManagerBuilder builder) throws Exception {
    builder
      .userDetailsService(userDetailsService)
      .passwordEncoder(passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
