package org.gfa.avusfoxticketbackend.repositories;

import org.gfa.avusfoxticketbackend.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {}
