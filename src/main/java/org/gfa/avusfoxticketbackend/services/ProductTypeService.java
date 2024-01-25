package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.dtos.ProductTypeRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.ProductTypeResponseDTO;
import org.gfa.avusfoxticketbackend.models.ProductType;

public interface ProductTypeService {
  ProductTypeResponseDTO createProductType(ProductTypeRequestDTO productTypeRequestDTO);

  ProductType requestProductTypeDTOToProductTypeConvert(
      ProductTypeRequestDTO productTypeRequestDTO);

  ProductTypeResponseDTO productTypeToProductTypeResponseDTOConvert(ProductType productType);
}
