package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "carts")
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToMany
  @JoinTable(
      name = "cart_product_cart",
      joinColumns = @JoinColumn(name = "cart_product_id"),
      inverseJoinColumns = @JoinColumn(name = "cart_id"))
  List<CartProduct> productList;

  @OneToOne(mappedBy = "cart")
  User user;

  private Date lastActivity;

  public Cart(Long id, List<CartProduct> productList, User user, Date lastActivity) {
    this.id = id;
    this.productList = productList;
    this.user = user;
    this.lastActivity = lastActivity;
  }

  public Cart(User user) {
    this.user = user;
    this.lastActivity = new Date(System.currentTimeMillis());
    this.productList = new ArrayList<>();
  }

  public Cart() {
    this.lastActivity = new Date(System.currentTimeMillis());
    this.productList = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<CartProduct> getProductList() {
    return productList;
  }

  public void setProductList(List<CartProduct> productList) {
    this.productList = productList;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Date getLastActivity() {
    return lastActivity;
  }

  public void setLastActivity(Date lastRemindedAt) {
    this.lastActivity = lastRemindedAt;
  }

  public Optional<CartProduct> getCartProductFromCart(Product product){
    Optional<CartProduct> cartProductOptional = Optional.empty();
    for (CartProduct cartProduct : productList){
      if (cartProduct.getProduct().equals(product)) {
        cartProductOptional = Optional.of(cartProduct);
        break;
      }
    }
    return cartProductOptional;
  }

  @Override
  public String toString() {
    return "Cart{"
        + "id="
        + id
        + ", productList="
        + productList
        + ", user="
        + user
        + ", lastActivity="
        + lastActivity
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cart cart = (Cart) o;
    return Objects.equals(getId(), cart.getId())
        && Objects.equals(getProductList(), cart.getProductList())
        && Objects.equals(getUser(), cart.getUser())
        && Objects.equals(getLastActivity(), cart.getLastActivity());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getProductList(), getUser(), getLastActivity());
  }
}
