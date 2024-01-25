package org.gfa.avusfoxticketbackend.services.impl;

import org.gfa.avusfoxticketbackend.dtos.ProductTypeRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.ProductTypeResponseDTO;
import org.gfa.avusfoxticketbackend.models.ProductType;
import org.gfa.avusfoxticketbackend.repositories.ProductTypeRepository;
import org.gfa.avusfoxticketbackend.services.ExceptionService;
import org.gfa.avusfoxticketbackend.services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {
  private final ProductTypeRepository productTypeRepository;
  private final ExceptionService exceptionService;

  @Autowired
  public ProductTypeServiceImpl(
      ProductTypeRepository productTypeRepository, ExceptionService exceptionService) {
    this.productTypeRepository = productTypeRepository;
    this.exceptionService = exceptionService;
  }

  @Override
  public ProductTypeResponseDTO createProductType(ProductTypeRequestDTO productTypeRequestDTO) {
    exceptionService.checkProductTypeRequestDTOErrors(productTypeRequestDTO);
    ProductType newProductType = requestProductTypeDTOToProductTypeConvert(productTypeRequestDTO);
    productTypeRepository.save(newProductType);
    return productTypeToProductTypeResponseDTOConvert(newProductType);
  }

  @Override
  public ProductType requestProductTypeDTOToProductTypeConvert(
      ProductTypeRequestDTO productTypeRequestDTO) {
    return new ProductType(productTypeRequestDTO.getName());
  }

  @Override
  public ProductTypeResponseDTO productTypeToProductTypeResponseDTOConvert(
      ProductType productType) {
    return new ProductTypeResponseDTO(productType.getId(), productType.getTypeName());
  }
}
