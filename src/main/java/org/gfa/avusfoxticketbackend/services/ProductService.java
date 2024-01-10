package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.models.Product;

public interface ProductService {
  ApiProductsDTO getApiProductsDto();

  ProductDTO toProductDto(Product product);

  ProductDTO updateProduct(RequestProductDTO requestProductDTO, Long id);

}
