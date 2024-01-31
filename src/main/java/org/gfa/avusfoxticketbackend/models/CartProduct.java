package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;
import java.util.Objects;
import org.gfa.avusfoxticketbackend.dtos.CartProductDTO;

@Entity
@Table(name = "cart_products")
public class CartProduct {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int quantity;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "cart_id")
  private Cart cart;

  public CartProduct() {}

  public CartProduct(Product currentProduct) {
    this.quantity = 1;
    this.product = currentProduct;
  }

  public CartProduct(Product product, Cart cart) {
    this.quantity = 1;
    this.product = product;
    this.cart = cart;
  }

  public CartProduct(int quantity, Product product, Cart cart) {
    this.quantity = quantity;
    this.product = product;
    this.cart = cart;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Cart getCart() {
    return cart;
  }

  public void setCart(Cart cart) {
    this.cart = cart;
  }

  @Override
  public String toString() {
    return "CartProduct{"
        + "id="
        + id
        + ", quantity="
        + quantity
        + ", product="
        + product
        + ", cart="
        + cart
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CartProduct that)) {
      return false;
    }
    return getQuantity() == that.getQuantity()
        && Objects.equals(getId(), that.getId())
        && Objects.equals(getProduct(), that.getProduct())
        && Objects.equals(getCart(), that.getCart());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getQuantity(), getProduct(), getCart());
  }

  public CartProductDTO toCartProductDTO() {
    return new CartProductDTO(product.getId(), product.getName(), quantity);
  }
}
