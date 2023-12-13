package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.ApiProductsDTO;
import org.gfa.avusfoxticketbackend.dtos.ProductDTO;
import org.gfa.avusfoxticketbackend.models.Product;

public interface ProductService {
  ApiProductsDTO getApiProductsDto();

  ProductDTO toProductDto(Product product);
}
