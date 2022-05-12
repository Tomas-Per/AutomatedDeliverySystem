package lt.vu.ads.models.order.json;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lt.vu.ads.models.order.Order;
import lt.vu.ads.models.orderInfo.OrderInfo;

import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderListView {
    private Long id;
    private String orderCode;
    private String orderStatus;

    public static OrderListView of(Order order) {
        List<OrderInfo> orderInfo = order.getOrderInfoList();
        orderInfo.sort(Comparator.comparing(OrderInfo::getDate));
        return OrderListView.builder()
                .id(order.getId())
                .orderCode(order.getOrderCode())
                .orderStatus(!orderInfo.isEmpty() ? orderInfo.get(0).getOrderStatus().toString() : null)
                .build();
    }
}
