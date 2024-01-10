package org.gfa.avusfoxticketbackend.repositories;

import org.gfa.avusfoxticketbackend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
}
