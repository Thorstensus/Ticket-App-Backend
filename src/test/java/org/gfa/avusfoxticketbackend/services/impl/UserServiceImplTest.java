package org.gfa.avusfoxticketbackend.services.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.gfa.avusfoxticketbackend.config.JwtService;
import org.gfa.avusfoxticketbackend.dtos.CartRequestDTO;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  private ExceptionServiceImpl exceptionService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private ProductServiceImpl productService;

  @Mock
  private JwtService jwtService;

  @InjectMocks
  private UserServiceImpl userService;


  @Test
  void saveProductToCartThrowsProductNotFoundError() {
    CartRequestDTO request = new CartRequestDTO(1L);
    ApiRequestException exception = new ApiRequestException("/api/cart","Product doesn't exist.");
    HttpServletRequest mockRequest = mock(HttpServletRequest.class);

    doThrow(exception)
            .when(exceptionService)
            .handleCartErrors(request);

    ApiRequestException thrownException = assertThrows(
            ApiRequestException.class,
            () -> {
              userService.saveProductToCart(request,mockRequest);
              });
    assertEquals(exception.getEndpoint(),thrownException.getEndpoint());
    assertEquals(exception.getMessage(),thrownException.getMessage());
  }

  @Test
  void saveProductToCartWorksWithCorrectId() {

  }

}