package org.gfa.avusfoxticketbackend.unit.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.gfa.avusfoxticketbackend.dtos.RequestProductDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseProductDTO;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.Product;
import org.gfa.avusfoxticketbackend.models.ProductType;
import org.gfa.avusfoxticketbackend.repositories.ProductRepository;
import org.gfa.avusfoxticketbackend.repositories.ProductTypeRepository;
import org.gfa.avusfoxticketbackend.services.impl.ExceptionServiceImpl;
import org.gfa.avusfoxticketbackend.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @Mock private ProductRepository productRepository;

  @Mock private ExceptionServiceImpl exceptionService;

  @Mock private ProductTypeRepository productTypeRepository;

  @InjectMocks private ProductServiceImpl productService;

  @Test
  void createNewProductAndReturn_returnNewResponseProductDTO() {
    ProductType expectedProductType = new ProductType("pass");
    RequestProductDTO requestProductDTO =
        new RequestProductDTO("name", 12.0, 4, "description", "pass");
    Product product = new Product(1L, "name", 12.0, 4, "description", expectedProductType);
    doNothing().when(exceptionService).checkForRequestProductDTOError(requestProductDTO);
    when(productRepository.save(any(Product.class))).thenReturn(product);

    when(productTypeRepository.getProductTypeByTypeName(requestProductDTO.getType()))
        .thenReturn(expectedProductType);

    ResponseProductDTO savedProduct = productService.createNewProductAndReturn(requestProductDTO);

    Assertions.assertThat(savedProduct)
        .isNotNull()
        .hasFieldOrPropertyWithValue("name", "name")
        .hasFieldOrPropertyWithValue("price", 12.0)
        .hasFieldOrPropertyWithValue("duration", "4 hours")
        .hasFieldOrPropertyWithValue("description", "description")
        .hasFieldOrPropertyWithValue("type", "pass");
  }

  @Test
  void createNewProductAndReturn_throwExceptionNameMissing() {
    RequestProductDTO requestProductDTO = new RequestProductDTO();
    requestProductDTO.setPrice(12.0);
    requestProductDTO.setDuration(4);
    requestProductDTO.setDescription("description");
    requestProductDTO.setType("Adventure");
    ApiRequestException apiRequestException =
        new ApiRequestException("/api/products", "Name is required");

    doThrow(apiRequestException)
        .when(exceptionService)
        .checkForRequestProductDTOError(requestProductDTO);

    ApiRequestException thrownException =
        assertThrows(
            ApiRequestException.class,
            () -> {
              productService.createNewProductAndReturn(requestProductDTO);
            });
    assertEquals(apiRequestException.getEndpoint(), thrownException.getEndpoint());
    assertEquals(apiRequestException.getMessage(), thrownException.getMessage());
  }

  @Test
  void requestProductDTOToProductConvert_SameObjects() {
    RequestProductDTO requestProductDTO =
        new RequestProductDTO("name", 12.0, 4, "description", "pass");
    Product product =
        new Product(
            null,
            "name",
            12.0,
            4,
            "description",
            productTypeRepository.getProductTypeByTypeName("pass"));

    Product createdProduct = productService.requestProductDTOToProductConvert(requestProductDTO);

    Assertions.assertThat(createdProduct).isNotNull().isEqualTo(product);
  }

  @Test
  void requestProductDTOToProductConvert_DifferentObjects() {
    RequestProductDTO requestProductDTO =
        new RequestProductDTO("name", 12.0, 4, "description", "1 week adventure");
    Product product =
        new Product(
            null,
            "amen",
            12.0,
            4,
            "description",
            productTypeRepository.getProductTypeByTypeName("pass"));

    Product createdProduct = productService.requestProductDTOToProductConvert(requestProductDTO);

    Assertions.assertThat(createdProduct).isNotNull().isNotEqualTo(product);
  }

  @Test
  void productToResponseProductDTOConvert_SameObjects() {
    Product product = new Product(1L, "name", 12.0, 4, "description", new ProductType("pass"));
    ResponseProductDTO responseProductDTO =
        new ResponseProductDTO(1L, "name", 12.0, "4 hours", "description", "pass");

    ResponseProductDTO createdResponseProductDTO =
        productService.productToResponseProductDTOConvert(product);

    Assertions.assertThat(createdResponseProductDTO).isNotNull().isEqualTo(responseProductDTO);
  }

  @Test
  void productToResponseProductDTOConvert_DifferentObjects() {
    Product product = new Product(1L, "amen", 12.0, 4, "description", new ProductType("Adventure"));
    ResponseProductDTO responseProductDTO =
        new ResponseProductDTO(1L, "name", 12.0, "4", "description", "pass");

    ResponseProductDTO createdResponseProductDTO =
        productService.productToResponseProductDTOConvert(product);

    Assertions.assertThat(createdResponseProductDTO).isNotNull().isNotEqualTo(responseProductDTO);
  }

  @Test
  void setProductsOnSale_productDoesntExist_throwException() {
    ApiRequestException response =
        new ApiRequestException(
            "/api/admin/products/12/sale?durationOfSale=1&sale=0.2", "Product doesn't exist.");
    doThrow(response).when(exceptionService).throwProductNotFound();

    ApiRequestException thrownException =
        assertThrows(
            ApiRequestException.class,
            () -> {
              productService.setProductOnSale(12L, 1L, 0.2);
            });
    assertEquals(response.getEndpoint(), thrownException.getEndpoint());
    assertEquals(response.getMessage(), thrownException.getMessage());
  }

  @Test
  void setProductOnSale_productAlreadyOnSale_throwException() {
    ApiRequestException response =
        new ApiRequestException(
            "/api/admin/products/1/sale?durationOfSale=1&sale=0.5", "Product is already on sale.");
    Product product = new Product(1L, "product", 24.99, 4, "description", true);
    product.setProductType(new ProductType("cultural"));
    when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    doThrow(response).when(exceptionService).throwProductAlreadyOnSale();

    ApiRequestException thrownException =
        assertThrows(
            ApiRequestException.class,
            () -> {
              productService.setProductOnSale(1L, 1L, 0.5);
            });
    assertEquals(response.getEndpoint(), thrownException.getEndpoint());
    assertEquals(response.getMessage(), thrownException.getMessage());
  }

  @Test
  void setProductOnSale_SetProductOnSale_returnsDTO() {
    Product product = new Product(1L, "product", 100.0, 4, "description", false);
    product.setProductType(new ProductType("cultural"));
    Long now = System.currentTimeMillis() / 1000L;
    ResponseProductDTO response =
        new ResponseProductDTO(
            1L, "product", 80.00, "4", "description", "cultural", true, now, now + 100L);
    when(productRepository.findById(1L)).thenReturn(Optional.of(product));

    ResponseProductDTO responseProductDTO = productService.setProductOnSale(1L, 100L, 0.2);

    Assertions.assertThat(responseProductDTO).isNotNull().isEqualTo(response);
  }
}
