package org.gfa.avusfoxticketbackend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.dtos.CartRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.CartResponseDTO;
import org.gfa.avusfoxticketbackend.logging.LogHandlerInterceptor;
import org.gfa.avusfoxticketbackend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SecuredController {

  private final UserService userService;
  private final OrderService orderService;
  private final CartService cartService;

  @Autowired
  public SecuredController(
      UserService userService, OrderService orderService, CartService cartService) {
    this.userService = userService;
    this.orderService = orderService;
    this.cartService = cartService;
  }

  @PostMapping("/cart")
  public ResponseEntity<CartResponseDTO> addToCart(
      @RequestBody(required = false) CartRequestDTO cartRequestDTO,
      HttpServletRequest httpServletRequest) {
    LogHandlerInterceptor.object = List.of(cartRequestDTO, httpServletRequest);
    return ResponseEntity.status(200)
        .body(cartService.saveProductToCart(cartRequestDTO, httpServletRequest));
  }

  @PostMapping("/orders")
  public ResponseEntity<ResponseOrderSummaryDTO> order(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
    LogHandlerInterceptor.object = token;
    token = token.substring(7);
    userService.checkUserVerification(token);
    return ResponseEntity.status(200).body(orderService.saveOrdersFromCart(token));
  }

  @GetMapping("/orders")
  public ResponseEntity<ResponseOrderSummaryDTO> getAllOrders(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
    LogHandlerInterceptor.object = token;
    token = token.substring(7);
    return ResponseEntity.status(200).body(orderService.getOrderSummaryDTO(token));
  }
}
