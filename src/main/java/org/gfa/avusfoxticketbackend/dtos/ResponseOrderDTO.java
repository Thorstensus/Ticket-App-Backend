package org.gfa.avusfoxticketbackend.dtos;

import java.util.List;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;

public class ResponseOrderDTO extends ResponseDTO {

  private Long id;
  private String status;
  private String expiry;
  private List<ResponseOrderProductDTO> products;

  public ResponseOrderDTO() {}

  public ResponseOrderDTO(
      Long id, String status, String expiry, List<ResponseOrderProductDTO> products) {
    this.id = id;
    this.status = status;
    this.expiry = expiry;
    this.products = products;
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

  public List<ResponseOrderProductDTO> getProducts() {
    return products;
  }

  public void setProducts(List<ResponseOrderProductDTO> products) {
    this.products = products;
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
        + ", products="
        + products
        + '}';
  }
}
