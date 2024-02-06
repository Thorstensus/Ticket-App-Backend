package org.gfa.avusfoxticketbackend.config.models;

import jakarta.persistence.*;
import org.gfa.avusfoxticketbackend.models.User;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String token;

  private Date expiryDate;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  public RefreshToken() {}

  public RefreshToken(String token, Date expiryDate, User user) {
    this.token = token;
    this.expiryDate = expiryDate;
    this.user = user;
  }

  public RefreshToken(Long id, String token, Date expiryDate, User user) {
    this.id = id;
    this.token = token;
    this.expiryDate = expiryDate;
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Date getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(Date expiryDate) {
    this.expiryDate = expiryDate;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "RefreshToken{"
        + "id="
        + id
        + ", token='"
        + token
        + '\''
        + ", expiryDate="
        + expiryDate
        + ", user="
        + (user != null ? user.getName() : null)
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RefreshToken that)) {
      return false;
    }
    return Objects.equals(getId(), that.getId())
        && Objects.equals(getToken(), that.getToken())
        && Objects.equals(getExpiryDate(), that.getExpiryDate())
        && Objects.equals(getUser(), that.getUser());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getToken(), getExpiryDate(), getUser());
  }
}
