package org.gfa.avusfoxticketbackend.repositories;

import org.gfa.avusfoxticketbackend.models.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {}
