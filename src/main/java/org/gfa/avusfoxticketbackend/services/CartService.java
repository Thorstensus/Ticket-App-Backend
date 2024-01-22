package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.models.Cart;

public interface CartService {
  void save(Cart cart);

  void deleteById(Long id);

  String deleteCart(String token);
}
