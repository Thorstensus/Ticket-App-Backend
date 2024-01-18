package org.gfa.avusfoxticketbackend.services.impl;

import org.gfa.avusfoxticketbackend.models.CartItem;
import org.gfa.avusfoxticketbackend.repositories.CartItemRepository;
import org.gfa.avusfoxticketbackend.services.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

  private final CartItemRepository cartItemRepository;

  @Autowired
  public CartItemServiceImpl(CartItemRepository cartItemRepository) {
    this.cartItemRepository = cartItemRepository;
  }

  @Override
  public void saveCartItem(CartItem cartItem){
    cartItemRepository.save(cartItem);
  }

  @Override
  public Optional<CartItem> findCartItemById(Long id){
    return cartItemRepository.findById(id);
  }
}
