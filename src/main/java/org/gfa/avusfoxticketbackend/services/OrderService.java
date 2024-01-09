package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.OrderDTO;
import org.gfa.avusfoxticketbackend.dtos.OrderSummaryDTO;
import org.gfa.avusfoxticketbackend.models.Order;

public interface OrderService {
  void saveOrdersFromCart(String token);

  default OrderDTO getOrderDTO(Order order) {
    return new OrderDTO(
        order.getId(), order.getStatus(), order.getExpiry(), order.getProduct().getId());
  }

  OrderSummaryDTO getOrderSummaryDTO(String token);
}
