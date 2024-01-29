package org.gfa.avusfoxticketbackend.services;

import java.util.Optional;
import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.models.Cart;
import org.gfa.avusfoxticketbackend.models.CartProduct;
import org.gfa.avusfoxticketbackend.models.User;

public interface CartService {
  Optional<Cart> getCartById(Long id);

  Optional<Cart> getCartByUser(User user);

  void saveCart(Cart cart);

  CartResponseDTO saveProductToCart(CartRequestDTO cartRequestDTO, String token);

  ModifyCartResponseDTO modifyProductInCart(ModifyCartRequestDTO requestDTO, String token);

  void addCartItemToCartAndSave(User user, CartProduct cartProduct);

  void save(Cart cart);

  void deleteById(Long id);

  ResponseStatusMessageDTO deleteCart(String token);
}
