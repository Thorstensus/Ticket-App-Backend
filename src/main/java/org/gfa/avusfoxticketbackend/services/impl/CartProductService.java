package org.gfa.avusfoxticketbackend.services.impl;

import org.gfa.avusfoxticketbackend.models.CartProduct;
import org.gfa.avusfoxticketbackend.repositories.CartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartProductService {

    private final CartProductRepository cartProductRepository;

    @Autowired
    public CartProductService(CartProductRepository cartProductRepository) {
        this.cartProductRepository = cartProductRepository;
    }

    public void save(CartProduct cartProduct) {
        cartProductRepository.save(cartProduct);
    }

    public void deleteById(Long id) {
        cartProductRepository.deleteById(id);
    }
}
