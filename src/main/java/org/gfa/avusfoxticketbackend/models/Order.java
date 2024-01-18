package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String status;
  private String expiry;

  @ManyToOne
  @JoinColumn(name = "cart_item_id")
  private CartItem cartItem;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Order() {}

  public Order(String expiry, CartItem cartItem) {
    this.status = "not active";
    this.expiry = expiry;
    this.cartItem = cartItem;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getExpiry() {
    return expiry;
  }

  public void setExpiry(String expiry) {
    this.expiry = expiry;
  }

  public CartItem getProduct() {
    return cartItem;
  }

  public void setProduct(CartItem cartItem) {
    this.cartItem = cartItem;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
