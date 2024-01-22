package org.gfa.avusfoxticketbackend.services.impl;

import org.gfa.avusfoxticketbackend.models.OrderProduct;
import org.gfa.avusfoxticketbackend.repositories.OrderProductRepository;
import org.gfa.avusfoxticketbackend.services.OrderProductService;
import org.springframework.stereotype.Service;

@Service
public class OrderProductServiceImpl implements OrderProductService {

  private final OrderProductRepository orderProductRepository;

  public OrderProductServiceImpl(OrderProductRepository orderProductRepository) {
    this.orderProductRepository = orderProductRepository;
  }

  @Override
  public void save(OrderProduct orderProduct) {
    orderProductRepository.save(orderProduct);
  }
}
