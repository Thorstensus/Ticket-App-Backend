package org.gfa.avusfoxticketbackend.controllers;

import org.gfa.avusfoxticketbackend.dtos.ApiProductsDto;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainRestController {

    private final ProductService productService;

    @Autowired
    public MainRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<ApiProductsDto> getProducts() {
        return ResponseEntity.ok(productService.getApiProductsDto());
    }
}
