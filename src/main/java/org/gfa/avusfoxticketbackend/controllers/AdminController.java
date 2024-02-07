package org.gfa.avusfoxticketbackend.controllers;

import java.util.List;
import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.logging.LogHandlerInterceptor;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.gfa.avusfoxticketbackend.services.ProductTypeService;
import org.gfa.avusfoxticketbackend.services.UserService;
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

  @Autowired
  public AdminController(
      ProductService productService,
      UserService userService,
      ProductTypeService productTypeService) {
    this.productService = productService;
    this.userService = userService;
    this.productTypeService = productTypeService;
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

  @PatchMapping("/products/{productId}/sale")
  public ResponseEntity<ResponseProductDTO> productSale(
      @PathVariable Long productId, @RequestBody RequestSaleDTO requestSaleDTO) {
    LogHandlerInterceptor.object = requestSaleDTO;
    return ResponseEntity.status(200)
        .body(
            productService.setProductOnSale(
                productId, requestSaleDTO.getDurationOfSale(), requestSaleDTO.getSale()));
  }
}
