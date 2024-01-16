package org.gfa.avusfoxticketbackend.dtos;

import java.util.List;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;

public class ResponseOrderSummaryDTO extends ResponseDTO {

  private List<ResponseOrderDTO> orders;

  public ResponseOrderSummaryDTO() {}

  public ResponseOrderSummaryDTO(List<ResponseOrderDTO> orders) {
    this.orders = orders;
  }

  public List<ResponseOrderDTO> getOrders() {
    return orders;
  }

  public void setOrders(List<ResponseOrderDTO> orders) {
    this.orders = orders;
  }

  @Override
  public String toString() {
    return "ResponseOrderSummaryDTO{" + "orders=" + orders + '}';
  }
}
