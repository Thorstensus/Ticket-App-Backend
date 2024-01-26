package org.gfa.avusfoxticketbackend.services;

import java.util.Optional;
import org.gfa.avusfoxticketbackend.models.CartProduct;

public interface CartProductService {
  void save(CartProduct cartProduct);

  Optional<CartProduct> findCartProductById(Long id);

  void deleteById(Long id);
}
