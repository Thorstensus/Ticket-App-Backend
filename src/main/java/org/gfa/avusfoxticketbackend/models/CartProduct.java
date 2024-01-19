package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;
import org.gfa.avusfoxticketbackend.dtos.CartProductDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cart_products")
public class CartProduct {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToMany(mappedBy = "productList")
  private List<Cart> inCart;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @OneToMany(mappedBy = "cartProduct")
  private List<Order> orders;

  private int quantity;

  public CartProduct() {
    this.orders = new ArrayList<>();
  }

  public CartProduct(List<Cart> inCart, Product product, int quantity) {
    this.inCart = inCart;
    this.product = product;
    this.orders = new ArrayList<>();
    this.quantity = quantity;
  }

  public CartProduct(Product product){
    this.product=product;
    this.orders = new ArrayList<>();
    this.quantity=1;
  }

  public CartProduct(Long id, Product product, List<Order> orders, int quantity) {
    this.id = id;
    this.product = product;
    this.orders = orders;
    this.quantity = quantity;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public List<Cart> getInCart() {
    return inCart;
  }

  public void setInCart(List<Cart> inCart) {
    this.inCart = inCart;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  @Override
  public String toString() {
    return "CartProduct{" +
            "id=" + id +
            ", inCart=" + inCart +
            ", product=" + product +
            ", orders=" + orders +
            ", quantity=" + quantity +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CartProduct that)) return false;
    return getQuantity() == that.getQuantity() && Objects.equals(getId(), that.getId()) && Objects.equals(getInCart(), that.getInCart()) && Objects.equals(getProduct(), that.getProduct()) && Objects.equals(getOrders(), that.getOrders());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getInCart(), getProduct(), getOrders(), getQuantity());
  }

  public CartProductDTO toCartProductDTO() {
    return new CartProductDTO(product.getId(), product.getName(), quantity);
  }
}
