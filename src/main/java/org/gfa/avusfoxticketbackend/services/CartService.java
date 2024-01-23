package org.gfa.avusfoxticketbackend.services;

import java.util.Optional;
import org.gfa.avusfoxticketbackend.dtos.CartRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.CartResponseDTO;
import org.gfa.avusfoxticketbackend.dtos.ModifyCartRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.ModifyCartResponseDTO;
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

  void deleteCart(Cart cart);

  String deleteCart(String token);
}
