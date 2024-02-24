package org.gfa.avusfoxticketbackend.dtos.authdtos;

import org.gfa.avusfoxticketbackend.dtos.abstractdtos.RequestDTO;

import java.util.Objects;

public class RefreshTokenRequestDTO extends RequestDTO {
  private String token;

  public RefreshTokenRequestDTO() {
  }

  public RefreshTokenRequestDTO(String token) {
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
    return "RefreshTokenRequestDTO{"
            + "token='" + token + '\''
            + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RefreshTokenRequestDTO request)) {
      return false;
    }
    return Objects.equals(getToken(), request.getToken());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getToken());
  }
}
