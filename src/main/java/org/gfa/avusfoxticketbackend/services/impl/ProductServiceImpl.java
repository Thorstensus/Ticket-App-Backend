package org.gfa.avusfoxticketbackend.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.gfa.avusfoxticketbackend.dtos.ApiProductsDTO;
import org.gfa.avusfoxticketbackend.dtos.ProductTypeStatisticsDTO;
import org.gfa.avusfoxticketbackend.dtos.RequestProductDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseProductDTO;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.*;
import org.gfa.avusfoxticketbackend.repositories.OrderProductRepository;
import org.gfa.avusfoxticketbackend.repositories.ProductRepository;
import org.gfa.avusfoxticketbackend.repositories.TypeRepository;
import org.gfa.avusfoxticketbackend.services.ExceptionService;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ExceptionService exceptionService;
  private final TypeRepository typeRepository;
  private final OrderProductRepository orderProductRepository;

  @Autowired
  public ProductServiceImpl(
      ProductRepository productRepository,
      ExceptionService exceptionService,
      TypeRepository typeRepository,
      OrderProductRepository orderProductRepository) {
    this.productRepository = productRepository;
    this.exceptionService = exceptionService;
    this.typeRepository = typeRepository;
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
        product.getType().getTypeName());
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

    exceptionService.checkForRequestProductDTOError(requestProductDTO, productId);

    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(
                () ->
                    new ApiRequestException(
                        ("/api/products/" + productId), "Product with provided id doesn't exist."));

    if (!productRepository.existsByName(requestProductDTO.getName())) {

      product.setName(requestProductDTO.getName());
      product.setPrice(requestProductDTO.getPrice());
      product.setDuration(requestProductDTO.getDuration());
      product.setDescription(requestProductDTO.getDescription());
      product.setType(typeRepository.getProductTypeByTypeName(requestProductDTO.getType()));
      productRepository.save(product);

      return toResponseProductDto(product);
    } else {
      throw new ApiRequestException(("/api/products/" + productId), "Product name already exists!");
    }
  }

  public Optional<Product> getProductById(Long id) {
    return productRepository.findById(id);
  }

  @Override
  public void saveProduct(Product product) {
    productRepository.save(product);
  }

  public ResponseProductDTO createNewProductAndReturn(RequestProductDTO requestProductDTO) {
    exceptionService.checkForRequestProductDTOError(requestProductDTO);
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
        typeRepository.getProductTypeByTypeName(requestProductDTO.getType()));
  }

  @Override
  public ResponseProductDTO productToResponseProductDTOConvert(Product product) {
    return new ResponseProductDTO(
        product.getId(),
        product.getName(),
        product.getPrice(),
        String.valueOf(product.getDuration()),
        product.getDescription(),
        product.getType().getTypeName());
  }

  @Override
  public List<ProductTypeStatisticsDTO> getStatistics() {
    List<Product> products = productRepository.findAll();
    List<ProductType> types = typeRepository.findAll();
    List<OrderProduct> orderedProducts = orderProductRepository.findAll();
    List<ProductTypeStatisticsDTO> statistics = new ArrayList<>();
    for (ProductType type : types) {
      List<Product> productsWithThisType =
          productRepository.findProductsByProductTypeId(type.getId());
      Integer quantity = 0;
      Double priceSum = 0.0;
      for (Product product : productsWithThisType) {
        Double productPrice = product.getPrice();
        List<OrderProduct> orderProductsWithThisProductId =
            orderProductRepository.findOrderProductsByProductId(product.getId());
        for (OrderProduct orderProduct : orderProductsWithThisProductId) {
          quantity += orderProduct.getQuantity();
          priceSum += productPrice * orderProduct.getQuantity();
        }
      }
      ProductTypeStatisticsDTO productTypeStatisticsDTO =
          new ProductTypeStatisticsDTO(type.getTypeName(), quantity, priceSum);
      statistics.add(productTypeStatisticsDTO);
    }
    return statistics;
  }

  @Override
  public List<Object[]> customQuery() {
    List<Object[]> sum = orderProductRepository.findProductSalesSummary();
    return sum;
  }
}
