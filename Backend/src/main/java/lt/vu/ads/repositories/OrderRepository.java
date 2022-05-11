package lt.vu.ads.repositories;

import lt.vu.ads.models.order.Order;
import lt.vu.ads.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findOneById(@Param("id") Long id);
    List<Order> findAllBySourceUser(User user);
}
