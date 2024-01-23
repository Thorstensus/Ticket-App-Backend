package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.ResponseOrderDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseOrderSummaryDTO;
import org.gfa.avusfoxticketbackend.models.Order;

public interface OrderService {

  ResponseOrderDTO saveOrdersFromCart(String token);

  ResponseOrderDTO getOrderDTO(Order order);

  ResponseOrderSummaryDTO getOrderSummaryDTO(String token);
}
