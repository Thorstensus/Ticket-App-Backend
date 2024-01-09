package org.gfa.avusfoxticketbackend.dtos;

import java.util.List;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;

public class OrderSummaryDTO extends ResponseDTO {

  private List<OrderDTO> orders;

  public OrderSummaryDTO() {}

  public OrderSummaryDTO(List<OrderDTO> orders) {
    this.orders = orders;
  }

  public List<OrderDTO> getOrders() {
    return orders;
  }

  public void setOrders(List<OrderDTO> orders) {
    this.orders = orders;
  }
}
