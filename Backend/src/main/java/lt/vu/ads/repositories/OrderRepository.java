package lt.vu.ads.repositories;

import lt.vu.ads.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findOneById(@Param("id") Long id);
    Order findByOrderCode(@Param("orderCode") String orderCode);
}
