package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "product_cart",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_id"))
    List<Product> productList;

    @OneToOne(mappedBy = "cart")
    User user;

    private Date lastActivity;

    public Cart(Long id, List<Product> productList, User user, Date lastActivity) {
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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
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

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", productList=" + productList +
                ", user=" + user +
                ", lastActivity=" + lastActivity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(getId(), cart.getId()) && Objects.equals(getProductList(), cart.getProductList()) && Objects.equals(getUser(), cart.getUser()) && Objects.equals(getLastActivity(), cart.getLastActivity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProductList(), getUser(), getLastActivity());
    }
}
