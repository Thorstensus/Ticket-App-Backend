package org.gfa.avusfoxticketbackend.services.impl;

import org.gfa.avusfoxticketbackend.models.OrderProduct;
import org.gfa.avusfoxticketbackend.repositories.OrderProductRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderProductService {

  private final OrderProductRepository orderProductRepository;

  public OrderProductService(OrderProductRepository orderProductRepository) {
    this.orderProductRepository = orderProductRepository;
  }

  public void save(OrderProduct orderProduct) {
    orderProductRepository.save(orderProduct);
  }
}
