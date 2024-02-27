package org.gfa.avusfoxticketbackend.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.gfa.avusfoxticketbackend.dtos.ApiProductsDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseProductTypeStatisticsDTO;
import org.gfa.avusfoxticketbackend.dtos.RequestProductDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseProductDTO;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.*;
import org.gfa.avusfoxticketbackend.repositories.OrderProductRepository;
import org.gfa.avusfoxticketbackend.repositories.ProductRepository;
import org.gfa.avusfoxticketbackend.repositories.ProductTypeRepository;
import org.gfa.avusfoxticketbackend.services.ExceptionService;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ExceptionService exceptionService;
  private final ProductTypeRepository productTypeRepository;
  private final OrderProductRepository orderProductRepository;

  @Autowired
  public ProductServiceImpl(
      ProductRepository productRepository,
      ExceptionService exceptionService,
      ProductTypeRepository productTypeRepository,
      OrderProductRepository orderProductRepository) {
    this.productRepository = productRepository;
    this.exceptionService = exceptionService;
    this.productTypeRepository = productTypeRepository;
    this.orderProductRepository = orderProductRepository;
  }

  @Override
  public ResponseProductDTO toResponseProductDto(Product product) {
    return new ResponseProductDTO(
        product.getId(),
        product.getName(),
        product.getPrice(),
        String.valueOf(product.getDuration()),
        product.getDescription(),
        product.getProductType().getTypeName());
  }

  @Override
  public ApiProductsDTO getApiProductsDto() {
    return new ApiProductsDTO(
        new ArrayList<>(
            productRepository.findAll().stream()
                .map(this::toResponseProductDto)
                .collect(Collectors.toList())));
  }

  @Override
  public ResponseProductDTO updateProduct(RequestProductDTO requestProductDTO, Long productId) {

    exceptionService.checkForRequestProductDTOErrors(requestProductDTO);
    exceptionService.checkForUpdateProductErrors(productId);

    Product product = productRepository.findById(productId).get();

    product.setName(requestProductDTO.getName());
    product.setPrice(requestProductDTO.getPrice());
    product.setDuration(requestProductDTO.getDuration());
    product.setDescription(requestProductDTO.getDescription());
    product.setProductType(productTypeRepository.getProductTypeByTypeName(requestProductDTO.getType()));
    productRepository.save(product);

    return toResponseProductDto(product);
  }

  public Optional<Product> getProductById(Long id) {
    return productRepository.findById(id);
  }

  @Override
  public void saveProduct(Product product) {
    productRepository.save(product);
  }

  public ResponseProductDTO createNewProductAndReturn(RequestProductDTO requestProductDTO) {
    exceptionService.checkForRequestProductDTOErrors(requestProductDTO);
    Product newProduct = requestProductDTOToProductConvert(requestProductDTO);
    productRepository.save(newProduct);
    return productToResponseProductDTOConvert(newProduct);
  }

  @Override
  public Product requestProductDTOToProductConvert(RequestProductDTO requestProductDTO) {
    return new Product(
        requestProductDTO.getName(),
        requestProductDTO.getPrice(),
        requestProductDTO.getDuration(),
        requestProductDTO.getDescription(),
        productTypeRepository.getProductTypeByTypeName(requestProductDTO.getType()));
  }

  @Override
  public ResponseProductDTO productToResponseProductDTOConvert(Product product) {
    return new ResponseProductDTO(
        product.getId(),
        product.getName(),
        product.getPrice(),
        String.valueOf(product.getDuration()),
        product.getDescription(),
        product.getProductType().getTypeName());
  }

  @Override
  public List<ResponseProductTypeStatisticsDTO> getStatistics() {
    List<ResponseProductTypeStatisticsDTO> sum = orderProductRepository.findProductSalesSummary();
    return orderProductRepository.findProductSalesSummary();
  }
}
