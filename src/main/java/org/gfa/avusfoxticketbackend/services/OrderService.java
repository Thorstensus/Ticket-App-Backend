package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.ResponseOrderDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseOrderSummaryDTO;
import org.gfa.avusfoxticketbackend.models.Order;

public interface OrderService {
  void saveOrdersFromCart(String token);

  default ResponseOrderDTO getOrderDTO(Order order) {
    return new ResponseOrderDTO(
        order.getId(), order.getStatus(), order.getExpiry(), order.getProduct().getId());
  }

  ResponseOrderSummaryDTO getOrderSummaryDTO(String token);
}
