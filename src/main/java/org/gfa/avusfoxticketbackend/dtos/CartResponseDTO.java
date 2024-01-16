package org.gfa.avusfoxticketbackend.dtos;

public class CartResponseDTO {
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
}
