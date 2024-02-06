package org.gfa.avusfoxticketbackend.unit.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import org.gfa.avusfoxticketbackend.dtos.RequestProductDTO;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.repositories.ProductRepository;
import org.gfa.avusfoxticketbackend.repositories.ProductTypeRepository;
import org.gfa.avusfoxticketbackend.services.ProductTypeService;
import org.gfa.avusfoxticketbackend.services.impl.ExceptionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExceptionServiceImplTest {

  @InjectMocks ExceptionServiceImpl exceptionService;
  @Mock private HttpServletRequest mockHttpServletRequest;
  @Mock private ProductRepository productRepository;
  @Mock private ProductTypeRepository productTypeRepository;
  @Mock private ProductTypeService productTypeService;

  @Test
  void throwFieldIsRequired_Name() {
    when(mockHttpServletRequest.getRequestURI()).thenReturn("/api/admin/products");
    ApiRequestException apiRequestException =
        new ApiRequestException("/api/admin/products", "Name is required");
    ApiRequestException thrownException =
        assertThrows(
            ApiRequestException.class,
            () -> {
              exceptionService.throwFieldIsRequired("Name");
            });
    assertEquals("/api/admin/products", thrownException.getEndpoint());
    assertEquals("Name is required.", thrownException.getMessage());
  }

  @Test
  void throwFieldIsRequired_Description() {
    when(mockHttpServletRequest.getRequestURI()).thenReturn("/api/admin/products");

    ApiRequestException thrownException =
        assertThrows(
            ApiRequestException.class,
            () -> {
              exceptionService.throwFieldIsRequired("Description");
            });

    assertEquals("/api/admin/products", thrownException.getEndpoint());
    assertEquals("Description is required.", thrownException.getMessage());
  }

  @Test
  void productNameTaken() {
    when(mockHttpServletRequest.getRequestURI()).thenReturn("/api/admin/products");
    ApiRequestException thrownException =
        assertThrows(
            ApiRequestException.class,
            () -> {
              exceptionService.productNameTaken();
            });

    assertEquals("/api/admin/products", thrownException.getEndpoint());
    assertEquals("Product name already exists.", thrownException.getMessage());
  }

  @Test
  void checkForRequestProductDTOError_MissingBody() {
    ApiRequestException requestException =
        assertThrows(
            ApiRequestException.class,
            () -> {
              exceptionService.checkForRequestProductDTOError(null);
            });
    assertEquals("Body is required.", requestException.getMessage());
  }

  @Test
  void checkForRequestProductDTOError_MissingName() {
    RequestProductDTO requestProductDTO = new RequestProductDTO();
    requestProductDTO.setPrice(12.0);
    requestProductDTO.setDuration(4);
    requestProductDTO.setDescription("description");
    requestProductDTO.setType("Adventure");

    ApiRequestException requestException =
        assertThrows(
            ApiRequestException.class,
            () -> {
              exceptionService.checkForRequestProductDTOError(requestProductDTO);
            });
    assertEquals("Name is required.", requestException.getMessage());
  }

  @Test
  void checkForRequestProductDTOError_EmptyName() {
    RequestProductDTO requestProductDTO = new RequestProductDTO();
    requestProductDTO.setName("");
    requestProductDTO.setPrice(12.0);
    requestProductDTO.setDuration(4);
    requestProductDTO.setDescription("description");
    requestProductDTO.setType("Adventure");

    ApiRequestException requestException =
        assertThrows(
            ApiRequestException.class,
            () -> {
              exceptionService.checkForRequestProductDTOError(requestProductDTO);
            });
    assertEquals("Name is required.", requestException.getMessage());
  }

  @Test
  void checkForRequestProductDTOError_MissingDescription() {
    RequestProductDTO requestProductDTO = new RequestProductDTO();
    requestProductDTO.setName("name");
    requestProductDTO.setPrice(12.0);
    requestProductDTO.setDuration(4);
    requestProductDTO.setType("Adventure");

    ApiRequestException requestException =
        assertThrows(
            ApiRequestException.class,
            () -> {
              exceptionService.checkForRequestProductDTOError(requestProductDTO);
            });
    assertEquals("Description is required.", requestException.getMessage());
  }

  @Test
  void checkForRequestProductDTOError_EmptyDescription() {
    RequestProductDTO requestProductDTO = new RequestProductDTO();
    requestProductDTO.setName("name");
    requestProductDTO.setPrice(12.0);
    requestProductDTO.setDuration(4);
    requestProductDTO.setDescription("          ");
    requestProductDTO.setType("Adventure");

    ApiRequestException requestException =
        assertThrows(
            ApiRequestException.class,
            () -> {
              exceptionService.checkForRequestProductDTOError(requestProductDTO);
            });
    assertEquals("Description is required.", requestException.getMessage());
  }

  @Test
  void checkForRequestProductDTOError_MissingDuration() {
    RequestProductDTO requestProductDTO = new RequestProductDTO();
    requestProductDTO.setName("name");
    requestProductDTO.setPrice(12.0);
    requestProductDTO.setDescription("description");
    requestProductDTO.setType("pass");

    ApiRequestException requestException =
        assertThrows(
            ApiRequestException.class,
            () -> {
              exceptionService.checkForRequestProductDTOError(requestProductDTO);
            });
    assertEquals("Duration is required.", requestException.getMessage());
  }

  @Test
  void checkForRequestProductDTOError_MissingType() {
    RequestProductDTO requestProductDTO = new RequestProductDTO();
    requestProductDTO.setName("name");
    requestProductDTO.setPrice(12.0);
    requestProductDTO.setDescription("description");
    requestProductDTO.setDuration(4);

    ApiRequestException requestException =
        assertThrows(
            ApiRequestException.class,
            () -> {
              exceptionService.checkForRequestProductDTOError(requestProductDTO);
            });
    assertEquals("Type is required.", requestException.getMessage());
  }

  @Test
  void checkForRequestProductDTOError_MissingPrice() {
    RequestProductDTO requestProductDTO = new RequestProductDTO();
    requestProductDTO.setName("name");
    requestProductDTO.setDescription("description");
    requestProductDTO.setDuration(4);
    requestProductDTO.setType("Adventure");

    ApiRequestException requestException =
        assertThrows(
            ApiRequestException.class,
            () -> {
              exceptionService.checkForRequestProductDTOError(requestProductDTO);
            });
    assertEquals("Price is required.", requestException.getMessage());
  }

  @Test
  void checkForRequestProductDTOError_NameTaken() {
    RequestProductDTO requestProductDTO = new RequestProductDTO();
    requestProductDTO.setName("name");
    requestProductDTO.setPrice(12.0);
    requestProductDTO.setDuration(4);
    requestProductDTO.setDescription("description");
    requestProductDTO.setType("Adventure");
    when(productRepository.existsByName(requestProductDTO.getName())).thenReturn(true);

    ApiRequestException requestException =
        assertThrows(
            ApiRequestException.class,
            () -> {
              exceptionService.checkForRequestProductDTOError(requestProductDTO);
            });
    assertEquals("Product name already exists.", requestException.getMessage());
  }

  @Test
  void checkForRequestProductDTOError_ValidType() {
    RequestProductDTO requestProductDTO = new RequestProductDTO();
    requestProductDTO.setName("name");
    requestProductDTO.setPrice(12.0);
    requestProductDTO.setDuration(4);
    requestProductDTO.setDescription("description");
    requestProductDTO.setType("Not Valid");

    ApiRequestException requestException =
        assertThrows(
            ApiRequestException.class,
            () -> {
              exceptionService.checkForRequestProductDTOError(requestProductDTO);
            });
    assertEquals(
        "Product type doesn't exist. Please make product type first.",
        requestException.getMessage());
  }
}
