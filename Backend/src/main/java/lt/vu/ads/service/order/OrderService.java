package lt.vu.ads.service.order;

import lt.vu.ads.models.EnumsOrder.Size;
import lt.vu.ads.models.address.Address;
import lt.vu.ads.models.order.json.OrderCreateView;
import lt.vu.ads.models.order.json.OrderEditView;
import lt.vu.ads.models.order.json.OrderListView;
import lt.vu.ads.models.order.json.OrderView;
import lt.vu.ads.models.user.json.UserEmailView;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

@Validated
public interface OrderService {

    double calculatePrice(Address sourceAddress, Address destinationAddress, Size size);

    Date calculateArrivalTime(boolean isExpress);

    List<OrderListView> getOrders();

    OrderView getOrderById(Long orderId);

    List<OrderListView> getOrdersByEmail(String emailView);

    OrderView updateOrder(Long orderId, OrderEditView orderDetails);

    Long saveOrder(OrderCreateView order);
}