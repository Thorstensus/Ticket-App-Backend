package org.gfa.avusfoxticketbackend.services;

import jakarta.servlet.http.HttpServletRequest;
import org.gfa.avusfoxticketbackend.dtos.CartRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.CartResponseDTO;
import org.gfa.avusfoxticketbackend.models.Cart;
import org.gfa.avusfoxticketbackend.models.User;

import java.util.Optional;

public interface CartService {
  Optional<Cart> getCartById(Long id);

  Optional<Cart> getCartByUser(User user);

  void saveCart(Cart cart);

  void deleteCart(Cart cart);

  CartResponseDTO saveProductToCart(
      CartRequestDTO cartRequestDTO, HttpServletRequest httpServletRequest);
}
