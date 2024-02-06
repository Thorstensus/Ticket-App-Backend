package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.gfa.avusfoxticketbackend.config.models.RefreshToken;
import org.gfa.avusfoxticketbackend.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String email;
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  private Boolean isVerified;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Order> orders;

  @OneToOne(mappedBy = "user")
  private Cart cart;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private RefreshToken refreshToken;

  public User() {
    this.role = Role.USER;
    this.isVerified = false;
    this.orders = new ArrayList<>();
  }

  public User(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = Role.USER;
    this.isVerified = false;
    this.orders = new ArrayList<>();
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Boolean getVerified() {
    return isVerified;
  }

  public void setVerified(Boolean verified) {
    isVerified = verified;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean isVerified() {
    return isVerified;
  }

  public RefreshToken getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(RefreshToken refreshToken) {
    this.refreshToken = refreshToken;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public Cart getCart() {
    return cart;
  }

  public void setCart(Cart cart) {
    this.cart = cart;
  }

  @Override
  public String toString() {
    return "User{"
            + "id=" + id
            + ", name='" + name + '\''
            + ", email='" + email + '\''
            + ", password='" + password + '\''
            + ", role=" + role
            + ", isVerified=" + isVerified
            + ", orders=" + orders
            + ", cart=" + cart
            + ", refreshToken=" + (refreshToken != null ? refreshToken.getToken() : null)
            + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User user)) {
      return false;
    }
    return Objects.equals(getId(),user.getId())
            && Objects.equals(getName(), user.getName())
            && Objects.equals(getEmail(), user.getEmail())
            && Objects.equals(getPassword(), user.getPassword())
            && getRole() == user.getRole()
            && Objects.equals(isVerified, user.isVerified)
            && Objects.equals(getOrders(), user.getOrders())
            && Objects.equals(getCart(), user.getCart())
            && Objects.equals(getRefreshToken(), user.getRefreshToken());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getEmail(), getPassword(), getRole(), isVerified, getOrders(), getCart(), getRefreshToken());
  }
}
