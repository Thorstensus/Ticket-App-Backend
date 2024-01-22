package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;
import org.gfa.avusfoxticketbackend.enums.Type;

@Entity
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private Double price;
  private Integer duration;
  private String description;

  @Enumerated(EnumType.STRING)
  private Type type;

  @OneToMany(mappedBy = "product")
  private List<CartProduct> cartProducts;

  @OneToMany(mappedBy = "product")
  private List<OrderProduct> orderProducts;

  public Product(String name, Double price, Integer duration, String description, Type type) {
    this.name = name;
    this.price = price;
    this.duration = duration;
    this.description = description;
    this.type = type;
  }

  public Product(
      Long id, String name, Double price, Integer duration, String description, Type type) {
    this.name = name;
    this.id = id;
    this.price = price;
    this.duration = duration;
    this.description = description;
    this.type = type;
  }

  public Product() {}

  public List<CartProduct> getCartProducts() {
    return cartProducts;
  }

  public void setCartProducts(List<CartProduct> cartProducts) {
    this.cartProducts = cartProducts;
  }

  public List<OrderProduct> getOrderProducts() {
    return orderProducts;
  }

  public void setOrderProducts(List<OrderProduct> orderProducts) {
    this.orderProducts = orderProducts;
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

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Product product)) {
      return false;
    }
    return Objects.equals(id, product.id)
        && Objects.equals(name, product.name)
        && Objects.equals(price, product.price)
        && Objects.equals(duration, product.duration)
        && Objects.equals(description, product.description)
        && type == product.type
        && Objects.equals(cartProducts, product.cartProducts)
        && Objects.equals(orderProducts, product.orderProducts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, price, duration, description, type, cartProducts, orderProducts);
  }
}
