package org.gfa.avusfoxticketbackend.repositories;

import java.util.List;
import org.gfa.avusfoxticketbackend.dtos.ResponseProductTypeStatisticsDTO;
import org.gfa.avusfoxticketbackend.models.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
  List<OrderProduct> findOrderProductsByProductId(Long id);

  @Query(
      "SELECT new org.gfa.avusfoxticketbackend.dtos.ResponseProductTypeStatisticsDTO(pt.typeName, "
          + "CAST(SUM(op.quantity) AS Integer), CAST(SUM(op.quantity * p.price) AS Double)) "
          + "FROM OrderProduct op "
          + "JOIN Product p ON op.product.id = p.id "
          + "JOIN ProductType pt ON p.productType.id = pt.id "
          + "GROUP BY pt.typeName")
  List<ResponseProductTypeStatisticsDTO> findProductSalesSummary();
}
