package org.gfa.avusfoxticketbackend.services.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.gfa.avusfoxticketbackend.config.JwtService;
import org.gfa.avusfoxticketbackend.dtos.CartRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.CartResponseDTO;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.Product;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

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
    Optional<User> user = Optional.of(new User("John","John@test.com","password"));
    //might need to change the type to ENUM later
    Optional<Product> product = Optional.of(new Product("ticket",69.0,420,"made of paper","transport"));
    CartRequestDTO request = new CartRequestDTO(1L);
    HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    UserServiceImpl mockedUserService = spy(userService);

    doNothing().when(exceptionService).handleCartErrors(request);
    //note for Barna: this part needs fixing
    doReturn(user).when(mockedUserService).extractUserFromRequest(mockRequest);
    doReturn(product).when(productService).getProductById(request.getProductId());
    doNothing().when(mockedUserService).saveUser(any(User.class));
    doNothing().when(productService).saveProduct(any(Product.class));

    CartResponseDTO response = userService.saveProductToCart(request,mockRequest);
    assertEquals(response.getProductId(),product.get().getId());
    assertEquals(response.getId(),user.get().getId());
    assertEquals(user.get().getCart(), List.of(product.get()));
  }

}