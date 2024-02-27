package org.gfa.avusfoxticketbackend.controllers;

import java.util.List;
import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.dtos.CreateNewsRequestDTO;
import org.gfa.avusfoxticketbackend.logging.LogHandlerInterceptor;
import org.gfa.avusfoxticketbackend.services.NewsService;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.gfa.avusfoxticketbackend.services.ProductTypeService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.gfa.avusfoxticketbackend.dtos.CreateNewsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

  private final ProductService productService;

  private final UserService userService;

  private final ProductTypeService productTypeService;

  private final NewsService newsService;

  @Autowired
  public AdminController(
          ProductService productService,
          UserService userService,
          ProductTypeService productTypeService,
          NewsService newsService) {
    this.productService = productService;
    this.userService = userService;
    this.productTypeService = productTypeService;
    this.newsService = newsService;
  }

  @PostMapping("/news-add")
  public ResponseEntity<CreateNewsResponseDTO> saveNews(@RequestBody(required = false) CreateNewsRequestDTO requestDTO) {
    return ResponseEntity.status(200).body(newsService.saveNews(requestDTO));
  }

  @GetMapping("/products")
  public ResponseEntity<ApiProductsDTO> getProducts() {
    return ResponseEntity.status(200).body(productService.getApiProductsDto());
  }

  @PatchMapping("/users/{userId}")
  public ResponseEntity<PatchResponseUserDTO> patchUser(
      @RequestBody(required = false) RequestUserDTO requestDTO,
      @PathVariable(required = false) Long userId) {
    LogHandlerInterceptor.object = requestDTO;
    return ResponseEntity.status(200).body(userService.patchUser(requestDTO, userId));
  }

  @PatchMapping({"/products/{productId}"})
  public ResponseEntity<ResponseProductDTO> updateProduct(
      @RequestBody(required = false) RequestProductDTO requestDTO,
      @PathVariable(required = false) Long productId) {
    LogHandlerInterceptor.object = requestDTO;
    return ResponseEntity.status(200)
        .body(productService.updateProduct(requestDTO, productId));
  }

  @PostMapping("/products")
  public ResponseEntity<ResponseProductDTO> createNewProduct(
      @RequestBody(required = false) RequestProductDTO requestDTO) {
    LogHandlerInterceptor.object = requestDTO;
    return ResponseEntity.status(200)
        .body(productService.createNewProductAndReturn(requestDTO));
  }

  @GetMapping("/product-types/stats")
  public ResponseEntity<List<ResponseProductTypeStatisticsDTO>> purchaseStatistics() {
    return ResponseEntity.status(200).body(productService.getStatistics());
  }
  
  @PostMapping("/product-types")
  public ResponseEntity<ProductTypeResponseDTO> createNewProductType(
      @RequestBody(required = false) ProductTypeRequestDTO requestDTO) {
    LogHandlerInterceptor.object = requestDTO;
    return ResponseEntity.status(200)
        .body(productTypeService.createProductType(requestDTO));
  }
}
