package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "product_type")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeName;

    @OneToMany(mappedBy = "productType")
    private List<Product> productList;

    public ProductType() {
    }

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
}
