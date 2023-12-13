package org.gfa.avusfoxticketbackend.dtos;

public class ResponseUserDTO {

  private Long id;
  private String email;
  private Boolean isAdmin;

  public ResponseUserDTO() {
    this.isAdmin = false;
  }

  public ResponseUserDTO(Long id, String email) {
    this.id = id;
    this.email = email;
    this.isAdmin = false;
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
}
