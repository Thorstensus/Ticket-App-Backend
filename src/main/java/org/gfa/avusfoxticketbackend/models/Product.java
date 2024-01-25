package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

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

  @ManyToOne
  @JoinColumn(name = "product_type_id")
  private ProductType productType;

  @OneToMany(mappedBy = "product")
  private List<CartProduct> cartProducts;

  @OneToMany(mappedBy = "product")
  private List<OrderProduct> orderProducts;

  public Product() {}

  public Product(
      String name, Double price, Integer duration, String description, ProductType productType) {
    this.name = name;
    this.price = price;
    this.duration = duration;
    this.description = description;
    this.productType = productType;
  }

  public Product(
      Long id,
      String name,
      Double price,
      Integer duration,
      String description,
      ProductType productType) {
    this.name = name;
    this.id = id;
    this.price = price;
    this.duration = duration;
    this.description = description;
    this.productType = productType;
  }

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

  public ProductType getType() {
    return productType;
  }

  public void setType(ProductType productType) {
    this.productType = productType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Product product)) {
      return false;
    }
    return Objects.equals(getId(), product.getId())
        && Objects.equals(getName(), product.getName())
        && Objects.equals(getPrice(), product.getPrice())
        && Objects.equals(getDuration(), product.getDuration())
        && Objects.equals(getDescription(), product.getDescription())
        && getType() == product.getType()
        && Objects.equals(getCartProducts(), product.getCartProducts())
        && Objects.equals(getOrderProducts(), product.getOrderProducts());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(),
        getName(),
        getPrice(),
        getDuration(),
        getDescription(),
        getType(),
        getCartProducts(),
        getOrderProducts());
  }
}
