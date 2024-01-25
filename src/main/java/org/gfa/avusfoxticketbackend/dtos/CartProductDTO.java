package org.gfa.avusfoxticketbackend.dtos;

public class CartProductDTO {
  private Long id;

  private String product;

  private int quantity;

  public CartProductDTO() {}

  public CartProductDTO(String product, int quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  public CartProductDTO(Long id, String product, int quantity) {
    this.id = id;
    this.product = product;
    this.quantity = quantity;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getProduct() {
    return product;
  }

  public void setProduct(String product) {
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
    return "CartProductDTO{"
        + "id="
        + id
        + ", product='"
        + product
        + '\''
        + ", quantity="
        + quantity
        + '}';
  }
}
