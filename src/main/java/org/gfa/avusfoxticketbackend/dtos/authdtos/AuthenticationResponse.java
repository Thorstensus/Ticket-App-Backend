package org.gfa.avusfoxticketbackend.dtos.authdtos;

import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;

import java.util.Objects;

public class AuthenticationResponse extends ResponseDTO {

  private String status;
  private String token;

  private String accessToken;

  public AuthenticationResponse() {}

  public AuthenticationResponse(String status, String token, String accessToken) {
    this.status = status;
    this.token = token;
    this.accessToken = accessToken;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  @Override
  public String toString() {
    return "AuthenticationResponse{"
        + "status='"
        + status
        + '\''
        + ", token='"
        + token
        + '\''
        + ", accessToken='"
        + accessToken
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AuthenticationResponse that)) {
      return false;
    }
    return Objects.equals(getStatus(), that.getStatus())
        && Objects.equals(getToken(), that.getToken())
        && Objects.equals(getAccessToken(), that.getAccessToken());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getStatus(), getToken(), getAccessToken());
  }
}
