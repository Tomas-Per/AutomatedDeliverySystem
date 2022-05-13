package lt.vu.ads.models.orderInfo.json;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lt.vu.ads.models.EnumsOrder.OrderStatus;
import lt.vu.ads.models.EnumsOrder.Size;
import lt.vu.ads.models.order.Order;
import lt.vu.ads.models.orderInfo.OrderInfo;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderInfoView {
    private Long id;
    private Date date;
    private OrderStatus orderStatus;
    private String description;
    public static OrderInfoView of(Order order) {
        List<OrderInfo> orderInfo = order.getOrderInfoList();
        orderInfo.sort(Comparator.comparing(OrderInfo::getDate));
        OrderInfo newestOrderInfo = orderInfo.get(0);

        return OrderInfoView.builder()
                .id(newestOrderInfo.getId())
                .date(newestOrderInfo.getDate())
                .orderStatus(newestOrderInfo.getOrderStatus())
                .description(newestOrderInfo.getDescription())
                .build();
    }
}
