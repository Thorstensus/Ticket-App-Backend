package org.gfa.avusfoxticketbackend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.gfa.avusfoxticketbackend.dtos.CartRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.CartResponseDTO;
import org.gfa.avusfoxticketbackend.logging.LogHandlerInterceptor;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SecuredController {

  private final ProductService productService;
  private final NewsService newsService;
  private final UserService userService;

  @Autowired
  public SecuredController(
      ProductService productService, NewsService newsService, UserService userService) {
    this.productService = productService;
    this.newsService = newsService;
    this.userService = userService;
  }

  @PostMapping("/cart")
  public ResponseEntity<CartResponseDTO> addToCart(@RequestBody(required = false) CartRequestDTO cartRequestDTO, HttpServletRequest httpServletRequest) {
    LogHandlerInterceptor.object = List.of(cartRequestDTO,httpServletRequest);
    return ResponseEntity.status(200).body(userService.saveProductToCart(cartRequestDTO,httpServletRequest));
  }

}
