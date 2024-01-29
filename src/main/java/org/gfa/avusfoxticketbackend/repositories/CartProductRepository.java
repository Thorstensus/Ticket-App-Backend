package org.gfa.avusfoxticketbackend.repositories;

import org.gfa.avusfoxticketbackend.models.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {}
