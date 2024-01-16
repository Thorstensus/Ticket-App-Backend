package org.gfa.avusfoxticketbackend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.dtos.CartRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.CartResponseDTO;
import org.gfa.avusfoxticketbackend.logging.LogHandlerInterceptor;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.gfa.avusfoxticketbackend.services.OrderService;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SecuredController {

  private final ProductService productService;
  private final NewsService newsService;
  private final UserService userService;
  private final OrderService orderService;

  @Autowired
  public SecuredController(
          ProductService productService, NewsService newsService, UserService userService, OrderService orderService) {
    this.productService = productService;
    this.newsService = newsService;
    this.userService = userService;
    this.orderService = orderService;
  }

  @PostMapping("/cart")
  public ResponseEntity<CartResponseDTO> addToCart(@RequestBody(required = false) CartRequestDTO cartRequestDTO, HttpServletRequest httpServletRequest) {
    LogHandlerInterceptor.object = List.of(cartRequestDTO,httpServletRequest);
    return ResponseEntity.status(200).body(userService.saveProductToCart(cartRequestDTO,httpServletRequest));
  }

@PostMapping("/orders")
public ResponseEntity<ResponseOrderSummaryDTO> order(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
  LogHandlerInterceptor.object = token;
  token = token.substring(7);
  orderService.saveOrdersFromCart(token);
  return ResponseEntity.status(200).body(orderService.getCartOrderSummaryDTOandCleanCart(token));
}

  @GetMapping("/orders")
  public ResponseEntity<ResponseOrderSummaryDTO> getAllOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
    LogHandlerInterceptor.object = token;
    token = token.substring(7);
    return ResponseEntity.status(200).body(orderService.getOrderSummaryDTO(token));
  }

}
