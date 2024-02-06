package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product_type")
public class ProductType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String typeName;

  @OneToMany(mappedBy = "productType")
  private List<Product> productList;

  public ProductType() {}

  public ProductType(String typeName) {
    this.typeName = typeName;
  }

  public ProductType(String typeName, List<Product> productList) {
    this.typeName = typeName;
    this.productList = productList;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public List<Product> getProductList() {
    return productList;
  }

  public void setProductList(List<Product> productList) {
    this.productList = productList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ProductType that)) {
      return false;
    }
    return Objects.equals(getId(), that.getId())
        && Objects.equals(getTypeName(), that.getTypeName())
        && Objects.equals(getProductList(), that.getProductList());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getTypeName(), getProductList());
  }
}
