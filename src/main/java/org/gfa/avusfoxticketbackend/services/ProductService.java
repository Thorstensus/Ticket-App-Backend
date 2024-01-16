package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.models.Product;

import java.util.Optional;

public interface ProductService {
  ApiProductsDTO getApiProductsDto();

  ResponseProductDTO updateProduct(RequestProductDTO requestProductDTO, Long id);
  
  Optional<Product> getProductById(Long id);

  void saveProduct(Product product);

  ResponseProductDTO toResponseProductDto(Product product);

  ResponseProductDTO createNewProductAndReturn(RequestProductDTO requestProductDTO);

  Product requestProductDTOToProductConvert(RequestProductDTO requestProductDTO);

  ResponseProductDTO productToResponseProductDTOConvert(Product product);

}
