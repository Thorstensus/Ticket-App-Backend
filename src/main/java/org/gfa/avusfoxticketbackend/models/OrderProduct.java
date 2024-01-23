package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "order_products")
public class OrderProduct {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int quantity;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  public OrderProduct() {}

  public OrderProduct(int quantity, Product product, Order order) {
    this.quantity = quantity;
    this.product = product;
    this.order = order;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public double getTotalPrice() {
    return this.product.getPrice() * this.quantity;
  }
}
