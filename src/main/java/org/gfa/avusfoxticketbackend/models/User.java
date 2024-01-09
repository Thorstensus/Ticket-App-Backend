package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.aspectj.weaver.ast.Or;
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


  @OneToMany(mappedBy = "user")
  private List<Order> orders;

  @ManyToMany
  @JoinTable(
          name = "user_product",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "product_id")
  )
  private List<Product> cart;


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

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getVerified() {
    return isVerified;
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

  public void setVerified(Boolean verified) {
    isVerified = verified;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setEmail(String email) {
    this.email = email;
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
}
