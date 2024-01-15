package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.ApiProductsDTO;
import org.gfa.avusfoxticketbackend.dtos.RequestProductDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseProductDTO;
import org.gfa.avusfoxticketbackend.models.Product;

import java.util.Optional;

public interface ProductService {
  ApiProductsDTO getApiProductsDto();

  ProductDTO toProductDto(Product product);

  Optional<Product> getProductById(Long id);

  void saveProduct(Product product);

  ResponseProductDTO toResponseProductDto(Product product);

  ResponseProductDTO createNewProductAndReturn(RequestProductDTO requestProductDTO);

  Product requestProductDTOToProductConvert(RequestProductDTO requestProductDTO);

  ResponseProductDTO productToResponseProductDTOConvert(Product product);

}
