package org.gfa.avusfoxticketbackend.services.impl;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.Optional;
import org.gfa.avusfoxticketbackend.dtos.CartRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.CartResponseDTO;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.Cart;
import org.gfa.avusfoxticketbackend.models.Product;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.CartRepository;
import org.gfa.avusfoxticketbackend.services.CartService;
import org.gfa.avusfoxticketbackend.services.ExceptionService;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final ExceptionService exceptionService;
  private final UserService userService;
  private final ProductService productService;

  @Autowired
  public CartServiceImpl(
      CartRepository cartRepository,
      ExceptionService exceptionService,
      UserService userService,
      ProductService productService) {
    this.cartRepository = cartRepository;
    this.exceptionService = exceptionService;
    this.userService = userService;
    this.productService = productService;
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
      CartRequestDTO cartRequestDTO, HttpServletRequest httpServletRequest) {
    exceptionService.handleCartErrors(cartRequestDTO);
    Optional<User> currentUserOptional = userService.extractUserFromRequest(httpServletRequest);
    Optional<Product> currentProductOptional =
        productService.getProductById(cartRequestDTO.getProductId());
    if (currentUserOptional.isPresent() && currentProductOptional.isPresent()) {
      User currentUser = currentUserOptional.get();
      Optional<Cart> currentUsersCartOptional = cartRepository.findCartByUser(currentUser);
      Product currentProduct = currentProductOptional.get();
      Cart currentUsersCart;
      if (currentUsersCartOptional.isEmpty()) {
        currentUsersCart = new Cart(currentUser);
        currentUser.setCart(currentUsersCart);
        currentUsersCart.getProductList().add(currentProduct);
      } else {
        currentUsersCart = currentUsersCartOptional.get();
        currentUsersCart.getProductList().add(currentProduct);
        currentUsersCart.setLastActivity(new Date(System.currentTimeMillis()));
      }
      saveCart(currentUsersCart);
      userService.saveUser(currentUser);
      return new CartResponseDTO(currentUser.getId(), currentProduct.getId());
    } else {
      throw new ApiRequestException("/api/cart", "Unknown Error");
    }
  }
}
