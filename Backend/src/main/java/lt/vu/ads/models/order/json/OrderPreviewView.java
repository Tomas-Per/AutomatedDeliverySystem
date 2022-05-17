package lt.vu.ads.models.order.json;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lt.vu.ads.models.EnumsOrder.Size;
import lt.vu.ads.models.order.Order;

import java.util.Date;

@Getter
@Setter
@Builder
public class OrderPreviewView {
    private Boolean isExpress;
    private Size size;
    private double price;
    private Date estimatedArrivalTime;
    public static OrderPreviewView of(OrderCreateView order) {
        return OrderPreviewView.builder()
                .isExpress(order.getIsExpress())
                .size(order.getSize())
                .price(order.getPrice())
                .estimatedArrivalTime(order.getEstimatedArrivalTime())
                .build();
    }
}
