package org.gfa.avusfoxticketbackend.services.impl;

import org.assertj.core.api.Assertions;
import org.gfa.avusfoxticketbackend.dtos.RequestProductDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseProductDTO;
import org.gfa.avusfoxticketbackend.enums.Type;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.Product;
import org.gfa.avusfoxticketbackend.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ExceptionServiceImpl exceptionService;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void createNewProductAndReturn_returnNewResponseProductDTO() {
        RequestProductDTO requestProductDTO =
                new RequestProductDTO("name", 12.0, 4, "description", "Adventure");
        Product product = new Product(1L, "name", 12.0, 4, "description", Type.Adventure);
        doNothing().when(exceptionService).checkForRequestProductDTOError(requestProductDTO);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ResponseProductDTO savedProduct = productService.createNewProductAndReturn(requestProductDTO);

        Assertions.assertThat(savedProduct)
                .isNotNull()
                .hasFieldOrPropertyWithValue("name", "name")
                .hasFieldOrPropertyWithValue("price", 12.0)
                .hasFieldOrPropertyWithValue("duration", "4 hours")
                .hasFieldOrPropertyWithValue("description", "description")
                .hasFieldOrPropertyWithValue("type", "Adventure");
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
                new RequestProductDTO("name", 12.0, 4, "description", "Adventure");
        Product product = new Product(null, "name", 12.0, 4, "description", Type.Adventure);

        Product createdProduct = productService.requestProductDTOToProductConvert(requestProductDTO);

        Assertions.assertThat(createdProduct).isNotNull().isEqualTo(product);
    }

    @Test
    void requestProductDTOToProductConvert_DifferentObjects() {
        RequestProductDTO requestProductDTO =
                new RequestProductDTO("name", 12.0, 4, "description", "Adventure");
        Product product = new Product(null, "amen", 12.0, 4, "description", Type.Adventure);

        Product createdProduct = productService.requestProductDTOToProductConvert(requestProductDTO);

        Assertions.assertThat(createdProduct).isNotNull().isNotEqualTo(product);
    }

    @Test
    void productToResponseProductDTOConvert_SameObjects() {
        Product product = new Product(1L, "name", 12.0, 4, "description", Type.Adventure);
        ResponseProductDTO responseProductDTO =
                new ResponseProductDTO(1L, "name", 12.0, "4", "description", "Adventure");

        ResponseProductDTO createdResponseProductDTO =
                productService.productToResponseProductDTOConvert(product);

        Assertions.assertThat(createdResponseProductDTO).isNotNull().isEqualTo(responseProductDTO);
    }

    @Test
    void productToResponseProductDTOConvert_DifferentObjects() {
        Product product = new Product(1L, "amen", 12.0, 4, "description", Type.Adventure);
        ResponseProductDTO responseProductDTO =
                new ResponseProductDTO(1L, "name", 12.0, "4", "description", "Adventure");

        ResponseProductDTO createdResponseProductDTO =
                productService.productToResponseProductDTOConvert(product);

        Assertions.assertThat(createdResponseProductDTO).isNotNull().isNotEqualTo(responseProductDTO);
    }
}
