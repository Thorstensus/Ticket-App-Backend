package org.gfa.avusfoxticketbackend.unit.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import org.gfa.avusfoxticketbackend.config.services.JwtService;
import org.gfa.avusfoxticketbackend.dtos.CartRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.ModifyCartRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseStatusMessageDTO;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.*;
import org.gfa.avusfoxticketbackend.repositories.CartRepository;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.gfa.avusfoxticketbackend.services.CartProductService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.gfa.avusfoxticketbackend.services.impl.CartServiceImpl;
import org.gfa.avusfoxticketbackend.services.impl.ExceptionServiceImpl;
import org.gfa.avusfoxticketbackend.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {
  @Mock private ExceptionServiceImpl exceptionService;

  @Mock private UserService userService;

  @Mock private ProductServiceImpl productService;

  @Mock private CartRepository cartRepository;

  @Mock private CartProductService cartProductService;

  @Mock private UserRepository userRepository;

  @Mock private JwtService jwtService;
  @InjectMocks private CartServiceImpl cartService;

  @Test
  void saveProductToCartThrowsProductNotFoundError() {
    CartRequestDTO request = new CartRequestDTO(1L);
    ApiRequestException exception = new ApiRequestException("/api/cart", "Product doesn't exist.");
    String token = "";

    doThrow(exception).when(exceptionService).handleCartErrors(request);

    ApiRequestException thrownException =
        assertThrows(
            ApiRequestException.class,
            () -> {
              cartService.saveProductToCart(request, token);
            });
    assertEquals(exception.getEndpoint(), thrownException.getEndpoint());
    assertEquals(exception.getMessage(), thrownException.getMessage());
  }

  @Test
  void saveProductToCartWorks() {
    CartRequestDTO request = new CartRequestDTO(1L);
    String token = "muchJwtSuchWow";

    User user = new User("John", "john@doe.com", "hashedPassword_xD");
    Product product = new Product("basic", 5.0, 30, "a basic ticket", new ProductType("Adventure"));

    doNothing().when(exceptionService).handleCartErrors(request);
    doReturn(Optional.of(user)).when(userService).extractUserFromToken(token);
    doReturn(Optional.of(product)).when(productService).getProductById(request.getProductId());
    when(cartRepository.save(any(Cart.class))).thenReturn(null);
    doNothing().when(cartProductService).save(any(CartProduct.class));
    doNothing().when(userService).saveUser(any(User.class));

    cartService.saveProductToCart(request, token);

    assertEquals(user.getCart().getCartProducts().get(0).getProduct(), product);
    assertEquals(user.getCart().getCartProducts().get(0).getQuantity(), 1);
    assertEquals(user.getCart().getCartProducts().get(0).getCart(), user.getCart());
  }

  @Test
  void saveProductToCartWorksAfterMultipleUses() {
    CartRequestDTO request = new CartRequestDTO(1L);
    String token = "muchJwtSuchWow";

    User user = new User("John", "john@doe.com", "hashedPassword_xD");
    Product product = new Product("basic", 5.0, 30, "a basic ticket", new ProductType("Adventure"));

    doNothing().when(exceptionService).handleCartErrors(request);
    doReturn(Optional.of(user)).when(userService).extractUserFromToken(token);
    doReturn(Optional.of(product)).when(productService).getProductById(request.getProductId());
    when(cartRepository.save(any(Cart.class))).thenReturn(null);
    doNothing().when(cartProductService).save(any(CartProduct.class));
    doNothing().when(userService).saveUser(any(User.class));

    cartService.saveProductToCart(request, token);
    cartService.saveProductToCart(request, token);
    cartService.saveProductToCart(request, token);

    assertEquals(user.getCart().getCartProducts().get(0).getProduct(), product);
    assertEquals(user.getCart().getCartProducts().get(0).getQuantity(), 3);
    assertEquals(user.getCart().getCartProducts().get(0).getCart(), user.getCart());
  }

  @Test
  void modifyProductUpdatesTheQuantityOfACartProduct() {
    CartRequestDTO request = new CartRequestDTO(1L);
    ModifyCartRequestDTO modifyRequest = new ModifyCartRequestDTO(1L, 5);
    String token = "muchJwtSuchWow";

    User user = new User("John", "john@doe.com", "hashedPassword_xD");
    Product product = new Product("basic", 5.0, 30, "a basic ticket", new ProductType("Adventure"));

    doNothing().when(exceptionService).handleCartErrors(request);
    doNothing().when(exceptionService).handleModifyCartErrors(modifyRequest, user);
    doReturn(Optional.of(user)).when(userService).extractUserFromToken(token);
    doReturn(Optional.of(product))
        .when(productService)
        .getProductById(modifyRequest.getProductId());
    when(cartRepository.save(any(Cart.class))).thenReturn(null);
    doNothing().when(cartProductService).save(any(CartProduct.class));
    doNothing().when(userService).saveUser(any(User.class));
    cartService.saveProductToCart(request, token);
    when(cartRepository.findCartByUser(any(User.class))).thenReturn(Optional.of(user.getCart()));

    cartService.modifyProductInCart(modifyRequest, token);

    assertEquals(
        user.getCart().getCartProducts().get(0).getQuantity(), modifyRequest.getQuantity());
  }

  @Test
  void noCartForDeleting() {
    User user = new User();
    String token = "hahaToken";

    Mockito.lenient().when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

    assertNull(user.getCart());
    assertEquals(new ResponseStatusMessageDTO("No cart to delete"), cartService.deleteCart(token));
  }

  @Test
  void cartHasBeenDeleted() {
    User user = new User();
    user.setCart(new Cart());
    String token = "hahaToken";

    Mockito.lenient().when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
    doNothing().when(cartRepository).delete(user.getCart());

    assertEquals(
        new ResponseStatusMessageDTO("Cart has been deleted"), cartService.deleteCart(token));
  }
}
