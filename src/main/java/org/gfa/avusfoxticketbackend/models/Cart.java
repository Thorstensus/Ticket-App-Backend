package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "carts")
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CartProduct> cartProducts;

  private Date lastActivity;

  public Cart() {
    this.lastActivity = new Date(System.currentTimeMillis());
    this.cartProducts = new ArrayList<>();
  }

  public Cart(User user) {
    this.user = user;
    this.lastActivity = new Date(System.currentTimeMillis());
    this.cartProducts = new ArrayList<>();
  }

  public Cart(User user, List<CartProduct> cartProducts) {
    this.user = user;
    this.lastActivity = new Date(System.currentTimeMillis());
    this.cartProducts = cartProducts;
  }

  public Cart(Long id, List<CartProduct> productList, User user, Date lastActivity) {
    this.id = id;
    this.cartProducts = productList;
    this.user = user;
    this.lastActivity = lastActivity;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Date getLastActivity() {
    return lastActivity;
  }

  public void setLastActivity(Date lastActivity) {
    this.lastActivity = lastActivity;
  }

  public Optional<CartProduct> getCartProductFromCart(Product product) {
    Optional<CartProduct> cartProductOptional = Optional.empty();
    for (CartProduct cartProduct : cartProducts) {
      if (cartProduct.getProduct().equals(product)) {
        cartProductOptional = Optional.of(cartProduct);
        break;
      }
    }
    return cartProductOptional;
  }
}
