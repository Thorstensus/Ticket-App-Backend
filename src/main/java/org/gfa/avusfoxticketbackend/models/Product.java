package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;
import java.util.ArrayList;
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
  private List<CartProduct> cartProduct;

  public Product() {}

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

  public List<CartProduct> getCartItem() {
    return cartProduct;
  }

  public void setCartItem(List<CartProduct> cartProduct) {
    this.cartProduct = cartProduct;
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
        && getType() == product.getType();
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(), getName(), getPrice(), getDuration(), getDescription(), getType());
  }
}
