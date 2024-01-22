package org.gfa.avusfoxticketbackend.services.impl;

import org.gfa.avusfoxticketbackend.models.CartProduct;
import org.gfa.avusfoxticketbackend.repositories.CartProductRepository;
import org.gfa.avusfoxticketbackend.services.CartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartProductServiceImpl implements CartProductService {

  private final CartProductRepository cartProductRepository;

  @Autowired
  public CartProductServiceImpl(CartProductRepository cartProductRepository) {
    this.cartProductRepository = cartProductRepository;
  }

  @Override
  public void save(CartProduct cartProduct) {
    cartProductRepository.save(cartProduct);
  }

  @Override
  public void deleteById(Long id) {
    cartProductRepository.deleteById(id);
  }
}
