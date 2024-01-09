package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.ApiProductsDTO;
import org.gfa.avusfoxticketbackend.dtos.ProductDTO;
import org.gfa.avusfoxticketbackend.models.Product;

import java.util.Optional;

public interface ProductService {
  ApiProductsDTO getApiProductsDto();

  ProductDTO toProductDto(Product product);

  Optional<Product> getProductById(Long id);

  void saveProduct(Product product);
}
