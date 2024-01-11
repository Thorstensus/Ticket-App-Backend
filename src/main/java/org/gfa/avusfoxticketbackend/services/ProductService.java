package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.ApiProductsDTO;
import org.gfa.avusfoxticketbackend.dtos.RequestProductDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseProductDTO;
import org.gfa.avusfoxticketbackend.models.Product;

public interface ProductService {
  ApiProductsDTO getApiProductsDto();

  ResponseProductDTO toResponseProductDto(Product product);

  ResponseProductDTO createNewProductAndReturn(RequestProductDTO requestProductDTO);

  Product requestProductDTOToProductConvert(RequestProductDTO requestProductDTO);

  ResponseProductDTO productToResponseProductDTOConvert(Product product);
}
