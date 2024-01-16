package org.gfa.avusfoxticketbackend.dtos;

import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;

public class ResponseOrderDTO extends ResponseDTO {

  private Long id;
  private String status;
  private String expiry;
  private Long productId;

  public ResponseOrderDTO() {}

  public ResponseOrderDTO(Long id, String status, String expiry, Long productId) {
    this.id = id;
    this.status = status;
    this.expiry = expiry;
    this.productId = productId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getExpiry() {
    return expiry;
  }

  public void setExpiry(String expiry) {
    this.expiry = expiry;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProduct_id(Long productId) {
    this.productId = productId;
  }

  @Override
  public String toString() {
    return "ResponseOrderDTO{"
        + "id="
        + id
        + ", status='"
        + status
        + '\''
        + ", expiry='"
        + expiry
        + '\''
        + ", productId="
        + productId
        + '}';
  }
}
