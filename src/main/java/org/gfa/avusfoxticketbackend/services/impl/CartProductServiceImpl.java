package org.gfa.avusfoxticketbackend.services.impl;

import org.gfa.avusfoxticketbackend.models.CartProduct;
import org.gfa.avusfoxticketbackend.repositories.CartProductRepository;
import org.gfa.avusfoxticketbackend.services.CartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartProductServiceImpl implements CartProductService {

  private final CartProductRepository cartProductRepository;

  @Autowired
  public CartProductServiceImpl(CartProductRepository cartProductRepository) {
    this.cartProductRepository = cartProductRepository;
  }

  @Override
  public void saveCartProduct(CartProduct cartProduct){
    cartProductRepository.save(cartProduct);
  }

  @Override
  public Optional<CartProduct> findCartProductById(Long id){
    return cartProductRepository.findById(id);
  }
}
