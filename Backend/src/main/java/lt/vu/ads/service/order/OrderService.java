package lt.vu.ads.service.order;

import lt.vu.ads.models.order.json.*;
import lt.vu.ads.models.orderInfo.json.OrderInfoView;
import lt.vu.ads.models.user.json.UserEmailView;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

@Validated
public interface OrderService {

    OrderPreviewView calculatePriceAndDate(OrderCreateView orderView);

    Date calculateArrivalTime(Boolean isExpress);

    List<OrderListView> getOrders();

    OrderView getOrderById(Long orderId);

    OrderInfoView getOrderInfoById(Long orderId);

    List<OrderListView> getOrdersByEmail(UserEmailView emailView);

    OrderView updateOrder(Long orderId, OrderEditView orderDetails);

    Long saveOrder(OrderCreateView order);
}