package org.gfa.avusfoxticketbackend.repositories;

import org.gfa.avusfoxticketbackend.models.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    List<OrderProduct> findOrderProductsByProductId(Long id);
    @Query("SELECT p.id AS product_id, p.name AS product_name, pt.typeName AS product_type, " +
            "SUM(op.quantity) AS total_quantity_sold, SUM(op.quantity * p.price) AS total_cost " +
            "FROM OrderProduct op " +
            "JOIN Product p ON op.product.id = p.id " +
            "JOIN ProductType pt ON p.productType.id = pt.id " +
            "GROUP BY p.id, p.name, pt.typeName")
    List<Object[]> findProductSalesSummary();
}
