package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.models.CartProduct;

import java.util.Optional;

public interface CartProductService {
  void saveCartProduct(CartProduct cartProduct);

  Optional<CartProduct> findCartProductById(Long id);
}
