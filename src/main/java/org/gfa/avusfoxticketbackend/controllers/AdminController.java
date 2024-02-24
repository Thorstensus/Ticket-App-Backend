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

  @PostMapping("/news/add")
  public ResponseEntity<CreateNewsResponseDTO> saveNews(CreateNewsRequestDTO requestDTO) {
    return ResponseEntity.status(200).body(newsService.saveNews(requestDTO));
  }

  @GetMapping("/products")
  public ResponseEntity<ApiProductsDTO> getProducts() {
    return ResponseEntity.status(200).body(productService.getApiProductsDto());
  }

  @PatchMapping({"/users/{id}", "/users/"})
  public ResponseEntity<PatchResponseUserDTO> patchUser(
      @RequestBody(required = false) RequestUserDTO requestUserDTO,
      @PathVariable(required = false) Long id) {
    LogHandlerInterceptor.object = requestUserDTO;
    return ResponseEntity.status(200).body(userService.patchUser(requestUserDTO, id));
  }

  @PatchMapping({"/products/{productId}/", "/products/"})
  public ResponseEntity<ResponseProductDTO> updateProduct(
      @RequestBody(required = false) RequestProductDTO requestProductDTO,
      @PathVariable(required = false) Long productId) {
    LogHandlerInterceptor.object = requestProductDTO;
    return ResponseEntity.status(200)
        .body(productService.updateProduct(requestProductDTO, productId));
  }

  @PostMapping("/products")
  public ResponseEntity<ResponseProductDTO> createNewProduct(
      @RequestBody(required = false) RequestProductDTO requestProductDTO) {
    LogHandlerInterceptor.object = requestProductDTO;
    return ResponseEntity.status(200)
        .body(productService.createNewProductAndReturn(requestProductDTO));
  }

  @GetMapping("/product-types/stats")
  public ResponseEntity<List<ResponseProductTypeStatisticsDTO>> purchaseStatistics() {
    return ResponseEntity.status(200).body(productService.getStatistics());
  }
  
  @PostMapping("/product-types")
  public ResponseEntity<ProductTypeResponseDTO> createNewProductType(
      @RequestBody(required = false) ProductTypeRequestDTO productTypeRequestDTO) {
    LogHandlerInterceptor.object = productTypeRequestDTO;
    return ResponseEntity.status(200)
        .body(productTypeService.createProductType(productTypeRequestDTO));
  }
}
