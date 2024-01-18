package org.gfa.avusfoxticketbackend.services.impl;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import org.gfa.avusfoxticketbackend.dtos.CartRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.CartResponseDTO;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.Cart;
import org.gfa.avusfoxticketbackend.models.CartItem;
import org.gfa.avusfoxticketbackend.models.Product;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.CartItemRepository;
import org.gfa.avusfoxticketbackend.repositories.CartRepository;
import org.gfa.avusfoxticketbackend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final ExceptionService exceptionService;
  private final UserService userService;
  private final ProductService productService;

  private final CartItemService cartItemService;

  @Autowired
  public CartServiceImpl(
      CartRepository cartRepository,
      ExceptionService exceptionService,
      UserService userService,
      ProductService productService,
      CartItemService cartItemService) {
    this.cartRepository = cartRepository;
    this.exceptionService = exceptionService;
    this.userService = userService;
    this.productService = productService;
    this.cartItemService = cartItemService;
  }

  @Override
  public Optional<Cart> getCartById(Long id) {
    return cartRepository.findById(id);
  }

  @Override
  public Optional<Cart> getCartByUser(User user) {
    return cartRepository.findCartByUser(user);
  }

  @Override
  public void saveCart(Cart cart) {
    cartRepository.save(cart);
  }

  @Override
  public void deleteCart(Cart cart) {
    cartRepository.delete(cart);
  }

  @Override
  public CartResponseDTO saveProductToCart(
      CartRequestDTO cartRequestDTO,
      HttpServletRequest httpServletRequest) {
    exceptionService.handleCartErrors(cartRequestDTO);
    Optional<User> currentUserOptional = userService.extractUserFromRequest(httpServletRequest);
    Optional<Product> currentProductOptional = productService.getProductById(cartRequestDTO.getProductId());
    if (currentUserOptional.isPresent() && currentProductOptional.isPresent()) {
      User currentUser = currentUserOptional.get();
      Optional<Cart> currentUsersCartOptional = cartRepository.findCartByUser(currentUser);
      Product currentProduct = currentProductOptional.get();
      Cart currentUsersCart;
      CartItem currentCartItem = new CartItem(currentProduct);
      if (currentUsersCartOptional.isEmpty()) {
        currentUsersCart = new Cart(currentUser);
        currentUser.setCart(currentUsersCart);
        currentUsersCart.getProductList().add(currentCartItem);
      } else {
        currentUsersCart = currentUsersCartOptional.get();
        currentUsersCart.setLastActivity(Date.valueOf(LocalDate.now()));
        currentUsersCart.getProductList().add(currentCartItem);
      }
      cartItemService.saveCartItem(currentCartItem);
      productService.saveProduct(currentProduct);
      saveCart(currentUsersCart);
      userService.saveUser(currentUser);
      return new CartResponseDTO(currentUser.getId(), currentProduct.getId());
    } else {
      throw new ApiRequestException("/api/cart", "Unknown Error");
    }
  }
}
