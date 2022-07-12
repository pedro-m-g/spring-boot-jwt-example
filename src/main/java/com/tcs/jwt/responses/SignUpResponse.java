package com.tcs.jwt.responses;

public class SignUpResponse {

  private String username;

  public SignUpResponse(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

}
