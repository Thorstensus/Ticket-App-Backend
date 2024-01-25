package org.gfa.avusfoxticketbackend.dtos.authdtos;

import org.gfa.avusfoxticketbackend.dtos.abstractdtos.RequestDTO;

public class RefreshTokenRequest extends RequestDTO {
  private String token;

  public RefreshTokenRequest() {
  }

  public RefreshTokenRequest(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
