package org.gfa.avusfoxticketbackend.services;

import org.gfa.avusfoxticketbackend.models.CartProduct;

public interface CartProductService {
  void save(CartProduct cartProduct);

  void deleteById(Long id);
}
