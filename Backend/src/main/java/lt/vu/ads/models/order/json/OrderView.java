package lt.vu.ads.models.order.json;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lt.vu.ads.models.EnumsOrder.Size;
import lt.vu.ads.models.address.Address;
import lt.vu.ads.models.order.Order;

import java.util.Date;

@Getter
@Setter
@Builder
public class OrderView {
    private Long id;
    private String orderCode;
    private boolean isExpress;
    private boolean isFragile;
    private Size size;
    private double price;
    private Date date;
    private Date estimatedArrivalTime;
    private Date convenientArrivalTimeFrom;
    private Date convenientArrivalTimeTo;
    private Address sourceAddress;
    private Address destinationAddress;
    public static OrderView of(Order order) {
        return OrderView.builder()
                .id(order.getId())
                .orderCode(order.getOrderCode())
                .isExpress(order.getIsExpress())
                .isFragile(order.getIsFragile())
                .size(order.getSize())
                .price(order.getPrice())
                .date(order.getDate())
                .estimatedArrivalTime(order.getEstimatedArrivalTime())
                .convenientArrivalTimeFrom(order.getConvenientArrivalTimeFrom())
                .convenientArrivalTimeTo(order.getConvenientArrivalTimeTo())
                .sourceAddress(order.getSourceAddress())
                .destinationAddress(order.getDestinationAddress())
                .build();
    }
}
