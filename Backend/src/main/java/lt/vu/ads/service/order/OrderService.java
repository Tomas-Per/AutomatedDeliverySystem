package lt.vu.ads.service.order;

import lt.vu.ads.models.order.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface OrderService {

    List<Order> getOrders();

    ResponseEntity<Order> getOrderById(Long orderId);

    ResponseEntity<Order> updateOrder(Long orderId, Order orderDetails);

    Order saveOrder(Order order);
}