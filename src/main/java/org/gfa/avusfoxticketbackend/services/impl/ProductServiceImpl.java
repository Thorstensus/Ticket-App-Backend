package org.gfa.avusfoxticketbackend.services.impl;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import org.gfa.avusfoxticketbackend.dtos.ApiProductsDTO;
import org.gfa.avusfoxticketbackend.dtos.ProductDTO;
import org.gfa.avusfoxticketbackend.models.Product;
import org.gfa.avusfoxticketbackend.repositories.ProductRepository;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Autowired
  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public ProductDTO toProductDto(Product product) {
    return new ProductDTO(
        product.getId(),
        product.getName(),
        product.getPrice(),
        product.getDuration(),
        product.getDescription(),
        product.getType());
  }

  @Override
  public ApiProductsDTO getApiProductsDto() {
    return new ApiProductsDTO(
        new ArrayList<>(
            productRepository.findAll().stream()
                .map(this::toProductDto)
                .collect(Collectors.toList())));
  }

  @Override
  public Optional<Product> getProductById(Long id){
    return productRepository.findById(id);
  }

  @Override
  public void saveProduct(Product product){
    productRepository.save(product);
  }
}
