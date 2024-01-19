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
  private CartProduct cartProduct;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Order() {}

  public Order(String expiry, CartProduct cartProduct) {
    this.status = "not active";
    this.expiry = expiry;
    this.cartProduct = cartProduct;
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

  public CartProduct getProduct() {
    return cartProduct;
  }

  public void setProduct(CartProduct cartProduct) {
    this.cartProduct = cartProduct;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
