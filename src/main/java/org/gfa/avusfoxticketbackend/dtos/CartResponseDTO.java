package org.gfa.avusfoxticketbackend.dtos;

import java.util.Objects;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;

public class CartResponseDTO extends ResponseDTO {
  private Long id;

  private Long productId;

  public CartResponseDTO() {}

  public CartResponseDTO(Long id, Long productId) {
    this.id = id;
    this.productId = productId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  @Override
  public String toString() {
    return "CartResponseDTO{" + "id=" + id + ", productId=" + productId + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CartResponseDTO that)) {
      return false;
    }
    return Objects.equals(getId(), that.getId())
        && Objects.equals(getProductId(), that.getProductId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getProductId());
  }
}
