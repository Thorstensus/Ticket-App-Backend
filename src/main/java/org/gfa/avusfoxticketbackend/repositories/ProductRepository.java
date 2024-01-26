package org.gfa.avusfoxticketbackend.repositories;

import java.util.List;
import org.gfa.avusfoxticketbackend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  boolean existsByName(String name);

  List<Product> findProductsByProductTypeId(Long id);
}
