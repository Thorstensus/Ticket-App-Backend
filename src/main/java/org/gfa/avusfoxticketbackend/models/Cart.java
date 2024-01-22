package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "cart")
  private List<CartProduct> cartProducts;

  private Date lastActivity;

  public Cart() {
    this.lastActivity = new Date(System.currentTimeMillis());
  }

  public Cart(User user, List<CartProduct> cartProducts) {
    this.user = user;
    this.cartProducts = cartProducts;
    this.lastActivity = new Date(System.currentTimeMillis());
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<CartProduct> getCartProducts() {
    return cartProducts;
  }

  public void setCartProducts(List<CartProduct> cartProducts) {
    this.cartProducts = cartProducts;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getLastActivity() {
    return lastActivity;
  }

  public void setLastActivity(Date lastActivity) {
    this.lastActivity = lastActivity;
  }
}
