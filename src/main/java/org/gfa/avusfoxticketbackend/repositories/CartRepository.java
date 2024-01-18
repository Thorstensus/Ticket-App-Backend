package org.gfa.avusfoxticketbackend.repositories;

import org.gfa.avusfoxticketbackend.models.Cart;
import org.gfa.avusfoxticketbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findCartByUser(User user);
}
