package org.gfa.avusfoxticketbackend.dtos;

import java.util.Objects;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.RequestDTO;

public class CartRequestDTO extends RequestDTO {

  private Long productId;

  public CartRequestDTO() {}

  public CartRequestDTO(Long productId) {
    this.productId = productId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  @Override
  public String toString() {
    return "CartRequestDTO{" + "productId=" + productId + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CartRequestDTO that)) {
      return false;
    }
    return Objects.equals(getProductId(), that.getProductId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getProductId());
  }
}
