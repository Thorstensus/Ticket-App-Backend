package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.models.CartItem;

import java.util.Optional;

public interface CartItemService {
  void saveCartItem(CartItem cartItem);

  Optional<CartItem> findCartItemById(Long id);
}
