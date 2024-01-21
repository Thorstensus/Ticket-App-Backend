package org.gfa.avusfoxticketbackend.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.gfa.avusfoxticketbackend.config.JwtService;
import org.gfa.avusfoxticketbackend.dtos.ResponseOrderDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseOrderProductDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseOrderSummaryDTO;
import org.gfa.avusfoxticketbackend.models.*;
import org.gfa.avusfoxticketbackend.repositories.OrderRepository;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.gfa.avusfoxticketbackend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final OrderProductService orderProductService;
  private final CartProductService cartProductService;
  private final CartService cartService;

  @Autowired
  public OrderServiceImpl(
          OrderRepository orderRepository,
          JwtService jwtService,
          UserRepository userRepository,
          OrderProductService orderProductService,
          CartProductService cartProductService,
          CartService cartService) {
    this.orderRepository = orderRepository;
    this.jwtService = jwtService;
    this.userRepository = userRepository;
    this.orderProductService = orderProductService;
    this.cartProductService = cartProductService;
    this.cartService = cartService;
  }

  @Override
  public ResponseOrderDTO saveOrdersFromCart(String token) {
    User user = userRepository.findByEmail(jwtService.extractUsername(token)).orElseThrow();
    Order order = new Order(user);
    orderRepository.save(order);
    List<OrderProduct> orderProducts = new ArrayList<>();
    for (CartProduct cartProduct : user.getCart().getCartProducts()) {
      OrderProduct orderProduct = new OrderProduct(cartProduct.getQuantity(), cartProduct.getProduct(), order);
      orderProductService.save(orderProduct);
      orderProducts.add(orderProduct);

    }
    order.setOrderProducts(orderProducts);
    orderRepository.save(order);
    List<Order> userOrders = user.getOrders();
    userOrders.add(order);
    user.setOrders(userOrders);

    // add email sending here

    for (CartProduct cartProduct : user.getCart().getCartProducts()) {
      cartProductService.deleteById(cartProduct.getId());
    }
    cartService.deleteById(user.getCart().getId());

    userRepository.save(user);

    return getOrderDTO(order);
  }

  @Override
  public ResponseOrderSummaryDTO getOrderSummaryDTO(String token) {
    User user = userRepository.findByEmail(jwtService.extractUsername(token)).orElseThrow();
    List<ResponseOrderDTO> responseOrderDTOList = new ArrayList<>();
    for (Order order : user.getOrders()) {
      responseOrderDTOList.add(getOrderDTO(order));
    }
    return new ResponseOrderSummaryDTO(responseOrderDTOList);
  }

  public ResponseOrderDTO getOrderDTO(Order order) {
    List<ResponseOrderProductDTO> orderProductDTOS = new ArrayList<>();
    for (OrderProduct product : order.getOrderProducts()) {
      orderProductDTOS.add(new ResponseOrderProductDTO(product.getId(), product.getQuantity()));
    }
    return new ResponseOrderDTO(
        order.getId(), order.getStatus(), order.getExpiry(), orderProductDTOS);
  }
}
