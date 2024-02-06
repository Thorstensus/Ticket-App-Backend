package org.gfa.avusfoxticketbackend.dtos.authdtos;

import java.util.Objects;
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

  @Override
  public String toString() {
    return "RefreshTokenRequest{"
            + "token='" + token + '\''
            + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RefreshTokenRequest request)) {
      return false;
    }
    return Objects.equals(getToken(), request.getToken());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getToken());
  }
}
