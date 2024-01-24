package org.gfa.avusfoxticketbackend.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.gfa.avusfoxticketbackend.config.JwtService;
import org.gfa.avusfoxticketbackend.dtos.ResponseOrderDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseOrderSummaryDTO;
import org.gfa.avusfoxticketbackend.models.Order;
import org.gfa.avusfoxticketbackend.models.Product;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.CustomUserRepository;
import org.gfa.avusfoxticketbackend.repositories.OrderRepository;
import org.gfa.avusfoxticketbackend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final JwtService jwtService;
  private final CustomUserRepository customUserRepository;

  @Autowired
  public OrderServiceImpl(
      OrderRepository orderRepository, JwtService jwtService, CustomUserRepository customUserRepository) {
    this.orderRepository = orderRepository;
    this.jwtService = jwtService;
    this.customUserRepository = customUserRepository;
  }

  @Override
  public void saveOrdersFromCart(String token) {
    User user = customUserRepository.findByEmail(jwtService.extractUsername(token)).orElseThrow();
    List<Order> userOrders = user.getOrders();
    for (Product product : user.getCart()) {
      Order order = new Order(null, product);
      order.setUser(user);
      userOrders.add(order);
      orderRepository.save(order);
    }
    customUserRepository.save(user);
  }

  public ResponseOrderSummaryDTO getCartOrderSummaryDTOandCleanCart(String token) {
    User user = customUserRepository.findByEmail(jwtService.extractUsername(token)).orElseThrow();
    List<Order> actualOrder = new ArrayList<>();
    for (int i = user.getOrders().size() - user.getCart().size(); i < user.getOrders().size(); i++) {
      actualOrder.add(user.getOrders().get(i));
    }
    user.setCart(new ArrayList<>());
    customUserRepository.save(user);
    List<ResponseOrderDTO> responseOrderDTOList = new ArrayList<>();
    for (Order order : actualOrder) {
      responseOrderDTOList.add(getOrderDTO(order));
    }
    return new ResponseOrderSummaryDTO(responseOrderDTOList);
  }

  @Override
  public ResponseOrderSummaryDTO getOrderSummaryDTO(String token) {
    User user = customUserRepository.findByEmail(jwtService.extractUsername(token)).orElseThrow();
    List<ResponseOrderDTO> responseOrderDTOList = new ArrayList<>();
    for (Order order : user.getOrders()) {
      responseOrderDTOList.add(getOrderDTO(order));
    }
    return new ResponseOrderSummaryDTO(responseOrderDTOList);
  }

  public ResponseOrderDTO getOrderDTO(Order order) {
    return new ResponseOrderDTO(
        order.getId(), order.getStatus(), order.getExpiry(), order.getProduct().getId());
  }
}
