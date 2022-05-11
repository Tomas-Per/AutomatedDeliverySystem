package lt.vu.ads.service.order;

import lt.vu.ads.models.order.json.OrderCreateView;
import lt.vu.ads.models.order.json.OrderEditView;
import lt.vu.ads.models.order.json.OrderListView;
import lt.vu.ads.models.order.json.OrderView;
import lt.vu.ads.models.user.json.UserEmailView;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface OrderService {

    List<OrderListView> getOrders();

    OrderView getOrderById(Long orderId);

    List<OrderListView> getOrdersByEmail(UserEmailView emailView);

    OrderView updateOrder(Long orderId, OrderEditView orderDetails);

    Long saveOrder(OrderCreateView order);
}