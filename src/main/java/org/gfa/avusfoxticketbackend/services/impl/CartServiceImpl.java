package org.gfa.avusfoxticketbackend.services.impl;

import org.gfa.avusfoxticketbackend.config.JwtService;
import org.gfa.avusfoxticketbackend.models.Cart;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.CartRepository;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.gfa.avusfoxticketbackend.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;

  private final UserRepository userRepository;

  private final JwtService jwtService;

  @Autowired
  public CartServiceImpl(
      CartRepository cartRepository, UserRepository userRepository, JwtService jwtService) {
    this.cartRepository = cartRepository;
    this.userRepository = userRepository;
    this.jwtService = jwtService;
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
  public String deleteCart(String token) {
    User user = userRepository.findByEmail(jwtService.extractUsername(token)).orElseThrow();
    if (user.getCart() == null) {
      return "No cart to delete";
    } else {
      cartRepository.delete(user.getCart());
      return "Cart has been deleted";
    }
  }
}
