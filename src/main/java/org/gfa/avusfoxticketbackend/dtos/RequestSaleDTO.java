package org.gfa.avusfoxticketbackend.dtos;

public class RequestSaleDTO {
  private Long durationOfSale;
  private Double sale;

  public RequestSaleDTO(Long durationOfSale, Double sale) {
    this.durationOfSale = durationOfSale;
    this.sale = sale;
  }

  public Long getDurationOfSale() {
    return durationOfSale;
  }

  public void setDurationOfSale(Long durationOfSale) {
    this.durationOfSale = durationOfSale;
  }

  public Double getSale() {
    return sale;
  }

  public void setSale(Double sale) {
    this.sale = sale;
  }

  @Override
  public String toString() {
    return "RequestSaleDTO{" + "durationOfSale=" + durationOfSale + ", sale=" + sale + '}';
  }
}
