package org.gfa.avusfoxticketbackend.controllers;

import java.util.List;
import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.logging.LogHandlerInterceptor;
import org.gfa.avusfoxticketbackend.models.News;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.gfa.avusfoxticketbackend.services.OrderService;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MainController {

  private final ProductService productService;
  private final NewsService newsService;
  private final UserService userService;
  private final OrderService orderService;

  @Autowired
  public MainController(
      ProductService productService,
      NewsService newsService,
      UserService userService,
      OrderService orderService) {
    this.productService = productService;
    this.newsService = newsService;
    this.userService = userService;
    this.orderService = orderService;
  }

  @GetMapping("/products")
  public ResponseEntity<ApiProductsDTO> getProducts() {
    return ResponseEntity.status(200).body(productService.getApiProductsDto());
  }

  @GetMapping("/news/")
  public ResponseEntity searchNews(@RequestParam(required = true) String search) {
    LogHandlerInterceptor.object = search;
    List<News> searchedNews = newsService.findAllNewsByTitleOrDescriptionContaining(search);
    if (!search.isEmpty() && !searchedNews.isEmpty()) {
      return ResponseEntity.status(200).body(new ArticlesResponseDTO(searchedNews));
    } else {
      throw new ApiRequestException("/api/news", "No news matching the searched text found.");
    }
  }

  @GetMapping("/news")
  public ResponseEntity<List<NewsResponseDTO>> getNews() {
    return ResponseEntity.status(200).body(newsService.getAllNewsDTOs());
  }

  @PatchMapping({"/users/{id}", "/users/"})
  public ResponseEntity<PatchResponseUserDTO> patchUser(
      @RequestBody(required = false) RequestUserDTO requestUserDTO,
      @PathVariable(required = false) Long id) {
    LogHandlerInterceptor.object = requestUserDTO;
    return ResponseEntity.status(200).body(userService.patchUser(requestUserDTO, id));
  }

  @PostMapping("/orders")
  public ResponseEntity<OrderSummaryDTO> order(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
    token = token.substring(7);
    orderService.saveOrdersFromCart(token);
    return ResponseEntity.status(200).body(orderService.getOrderSummaryDTO(token));
  }
}
