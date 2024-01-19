package org.gfa.avusfoxticketbackend.services.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import org.gfa.avusfoxticketbackend.dtos.CartRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.CartResponseDTO;
import org.gfa.avusfoxticketbackend.dtos.ModifyCartRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.ModifyCartResponseDTO;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.Cart;
import org.gfa.avusfoxticketbackend.models.CartProduct;
import org.gfa.avusfoxticketbackend.models.Product;
import org.gfa.avusfoxticketbackend.models.User;
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

  private final CartProductService cartProductService;

  @Autowired
  public CartServiceImpl(
      CartRepository cartRepository,
      ExceptionService exceptionService,
      UserService userService,
      ProductService productService,
      CartProductService cartProductService) {
    this.cartRepository = cartRepository;
    this.exceptionService = exceptionService;
    this.userService = userService;
    this.productService = productService;
    this.cartProductService = cartProductService;
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
          CartRequestDTO requestDTO,
          String token) {
    exceptionService.handleCartErrors(requestDTO);
    Optional<User> currentUserOptional = userService.extractUserFromToken(token);
    Optional<Product> currentProductOptional = productService.getProductById(requestDTO.getProductId());
    if (currentUserOptional.isPresent() && currentProductOptional.isPresent()) {
      User currentUser = currentUserOptional.get();
      Product currentProduct = currentProductOptional.get();
      CartProduct currentCartProduct = new CartProduct(currentProduct);
      addCartItemToCart(currentUser, currentCartProduct);
      return new CartResponseDTO(currentUser.getId(), currentProduct.getId());
    } else {
      throw new ApiRequestException("/api/cart", "Unknown Error");
    }
  }

  @Override
  public ModifyCartResponseDTO modifyProductInCart(
          ModifyCartRequestDTO requestDTO,
          String token) {
    Optional<User> currentUserOptional = userService.extractUserFromToken(token);
    if (currentUserOptional.isPresent()) {
      User currentUser = currentUserOptional.get();
      exceptionService.handleModifyCartErrors(requestDTO,currentUser);
      Cart currentUserCart = getCartByUser(currentUser).get();
      Product currentProduct = productService.getProductById(requestDTO.getProductId()).get();
      CartProduct currentCartProduct = currentUserCart.getCartProductFromCart(currentProduct).get();
      currentCartProduct.setQuantity(requestDTO.getQuantity());
      cartProductService.saveCartProduct(currentCartProduct);
      userService.saveUser(currentUser);
      saveCart(currentUserCart);
      return new ModifyCartResponseDTO(currentUserCart);
    } else {
      throw new ApiRequestException("/api/cart", "Unknown Error");
    }
  }

  @Override
  public void addCartItemToCart(User user, CartProduct cartProduct){
    Cart currentUserCart;
    if (user.getCart() == null) {
      currentUserCart = new Cart(user);
      user.setCart(currentUserCart);
      currentUserCart.getProductList().add(cartProduct);
    } else {
      currentUserCart = user.getCart();
      currentUserCart.setLastActivity(Date.valueOf(LocalDate.now()));
      currentUserCart.getProductList().add(cartProduct);
    }
    cartProductService.saveCartProduct(cartProduct);
    userService.saveUser(user);
    saveCart(currentUserCart);
  }
}
