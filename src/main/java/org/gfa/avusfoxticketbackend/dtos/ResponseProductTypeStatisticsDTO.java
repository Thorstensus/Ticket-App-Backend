package org.gfa.avusfoxticketbackend.dtos;

import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;

public class ResponseProductTypeStatisticsDTO extends ResponseDTO {
  private String productTypeName;
  private Integer quantitySold;
  private Double totalPrice;

  public ResponseProductTypeStatisticsDTO(
      String productTypeName, Integer quantitySold, Double totalPrice) {
    this.productTypeName = productTypeName;
    this.quantitySold = quantitySold;
    this.totalPrice = totalPrice;
  }

  public ResponseProductTypeStatisticsDTO() {}

  public String getProductTypeName() {
    return productTypeName;
  }

  public void setProductTypeName(String productTypeName) {
    this.productTypeName = productTypeName;
  }

  public Integer getQuantitySold() {
    return quantitySold;
  }

  public void setQuantitySold(Integer quantitySold) {
    this.quantitySold = quantitySold;
  }

  public Double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Double totalPrice) {
    this.totalPrice = totalPrice;
  }
}
