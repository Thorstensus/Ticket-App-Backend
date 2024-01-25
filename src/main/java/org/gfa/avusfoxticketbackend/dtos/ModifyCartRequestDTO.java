package org.gfa.avusfoxticketbackend.dtos;

import org.gfa.avusfoxticketbackend.dtos.abstractdtos.RequestDTO;

public class ModifyCartRequestDTO extends RequestDTO {

  private Long productId;

  private int quantity;

  public ModifyCartRequestDTO() {}

  public ModifyCartRequestDTO(Long productId, int quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    return "ModifyCartRequestDTO{" + "productId=" + productId + ", quantity=" + quantity + '}';
  }
}
