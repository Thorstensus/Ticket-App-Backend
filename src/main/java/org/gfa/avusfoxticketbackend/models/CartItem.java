package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cart_items")
public class CartItem {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @OneToMany(mappedBy = "cartItem")
  private List<Order> orders;

  private int quantity;

  public CartItem() {
    this.orders = new ArrayList<>();
  }

  public CartItem(Product product, int quantity) {
    this.product = product;
    this.orders = new ArrayList<>();
    this.quantity = quantity;
  }

  public CartItem(Product product){
    this.product=product;
    this.orders = new ArrayList<>();
    this.quantity=1;
  }

  public CartItem(Long id, Product product, List<Order> orders, int quantity) {
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

  @Override
  public String toString() {
    return "CartItem{" +
            "id=" + id +
            ", product=" + product +
            ", quantity=" + quantity +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CartItem cartItem)) return false;
    return getQuantity() == cartItem.getQuantity() && Objects.equals(getId(), cartItem.getId()) && Objects.equals(getProduct(), cartItem.getProduct());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getProduct(), getQuantity());
  }
}
