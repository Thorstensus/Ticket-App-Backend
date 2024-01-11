package org.gfa.avusfoxticketbackend.dtos;

import org.gfa.avusfoxticketbackend.enums.Role;

public class ResponseUserDTO {

  private Long id;
  private String email;
  private Boolean isAdmin;

  public ResponseUserDTO() {
    this.isAdmin = false;
  }

  public ResponseUserDTO(Long id, String email, Role role) {
    this.id = id;
    this.email = email;
    this.isAdmin = (role == Role.ADMIN);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Boolean getAdmin() {
    return isAdmin;
  }

  public void setAdmin(Boolean admin) {
    isAdmin = admin;
  }

  @Override
  public String toString() {
    return "ResponseUserDTO{"
        + "id="
        + id
        + ", email='"
        + email
        + '\''
        + ", isAdmin="
        + isAdmin
        + '}';
  }
}
