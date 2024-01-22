package org.gfa.avusfoxticketbackend.repositories;

import org.gfa.avusfoxticketbackend.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {}
