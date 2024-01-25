package org.gfa.avusfoxticketbackend.repositories;

import org.gfa.avusfoxticketbackend.models.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<ProductType, Long> {
  boolean existsByTypeName(String name);

  ProductType getProductTypeByTypeName(String name);
}
