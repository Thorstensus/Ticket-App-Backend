package org.gfa.avusfoxticketbackend.services.impl;

import org.gfa.avusfoxticketbackend.models.Cart;
import org.gfa.avusfoxticketbackend.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

  private final CartRepository cartRepository;

  @Autowired
  public CartService(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  public void save(Cart cart) {
    cartRepository.save(cart);
  }

  public void deleteById(Long id) {
    cartRepository.deleteById(id);
  }
}
