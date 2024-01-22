package org.gfa.avusfoxticketbackend.dtos;

import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;

public class ResponseOrderProductDTO extends ResponseDTO {
  private Long productId;
  private int quantity;

  public ResponseOrderProductDTO(Long productId, int quantity) {
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
}
