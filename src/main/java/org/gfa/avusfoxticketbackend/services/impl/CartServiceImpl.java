package org.gfa.avusfoxticketbackend.services.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.gfa.avusfoxticketbackend.config.services.JwtService;
import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.Cart;
import org.gfa.avusfoxticketbackend.models.CartProduct;
import org.gfa.avusfoxticketbackend.models.Product;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.CartRepository;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
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

  private final UserRepository userRepository;

  private final JwtService jwtService;

  @Autowired
  public CartServiceImpl(
      CartRepository cartRepository,
      ExceptionService exceptionService,
      UserService userService,
      ProductService productService,
      CartProductService cartProductService,
      UserRepository userRepository,
      JwtService jwtService) {
    this.cartRepository = cartRepository;
    this.exceptionService = exceptionService;
    this.userService = userService;
    this.productService = productService;
    this.cartProductService = cartProductService;
    this.userRepository = userRepository;
    this.jwtService = jwtService;
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
  public CartResponseDTO saveProductToCart(CartRequestDTO requestDTO, String token) {
    exceptionService.handleCartErrors(requestDTO);
    Optional<User> currentUserOptional = userService.extractUserFromToken(token);
    Optional<Product> currentProductOptional =
        productService.getProductById(requestDTO.getProductId());
    if (currentUserOptional.isPresent() && currentProductOptional.isPresent()) {
      User currentUser = currentUserOptional.get();
      Product currentProduct = currentProductOptional.get();
      CartProduct currentCartProduct = new CartProduct(currentProduct);
      addCartItemToCartAndSave(currentUser, currentCartProduct);
      return new CartResponseDTO(currentUser.getId(), currentProduct.getId());
    } else {
      throw new ApiRequestException("/api/cart", "Unknown Error");
    }
  }

  @Override
  public ModifyCartResponseDTO modifyProductInCart(ModifyCartRequestDTO requestDTO, String token) {
    Optional<User> currentUserOptional = userService.extractUserFromToken(token);
    if (currentUserOptional.isPresent()) {
      User currentUser = currentUserOptional.get();
      exceptionService.handleModifyCartErrors(requestDTO, currentUser);
      Cart currentUserCart = getCartByUser(currentUser).get();
      Product currentProduct = productService.getProductById(requestDTO.getProductId()).get();
      CartProduct currentCartProduct = currentUserCart.getCartProductFromCart(currentProduct).get();
      currentCartProduct.setQuantity(requestDTO.getQuantity());
      cartProductService.save(currentCartProduct);
      userService.saveUser(currentUser);
      saveCart(currentUserCart);
      List<CartProductDTO> responseList = new ArrayList<>();
      for (CartProduct cartProduct : currentUserCart.getCartProducts()) {
        responseList.add(cartProduct.toCartProductDTO());
      }
      return new ModifyCartResponseDTO(responseList);
    } else {
      throw new ApiRequestException("/api/cart", "Unknown Error");
    }
  }

  @Override
  public void addCartItemToCartAndSave(User user, CartProduct cartProduct) {
    Cart currentUserCart;
    if (user.getCart() == null) {
      currentUserCart = new Cart(user);
      user.setCart(currentUserCart);
    } else {
      currentUserCart = user.getCart();
      currentUserCart.setLastActivity(Date.valueOf(LocalDate.now()));
    }
    Optional<CartProduct> currentCartProductOptional =
        currentUserCart.getCartProductFromCart(cartProduct.getProduct());
    if (currentCartProductOptional.isPresent()) {
      cartProduct = currentCartProductOptional.get();
      cartProduct.setQuantity(cartProduct.getQuantity() + 1);
    } else {
      cartProduct.setCart(currentUserCart);
      currentUserCart.getCartProducts().add(cartProduct);
    }
    saveCart(currentUserCart);
    cartProductService.save(cartProduct);
    userService.saveUser(user);
  }

  @Override
  public void save(Cart cart) {
    cartRepository.save(cart);
  }

  @Override
  public void deleteById(Long id) {
    cartRepository.deleteById(id);
  }

  @Override
  public ResponseStatusMessageDTO deleteCart(String token) {
    User user = userRepository.findByEmail(jwtService.extractUsername(token)).orElseThrow();
    if (user.getCart() == null) {
      return new ResponseStatusMessageDTO("No cart to delete");
    } else {
      cartRepository.delete(user.getCart());
      return new ResponseStatusMessageDTO("Cart has been deleted");
    }
  }
}
