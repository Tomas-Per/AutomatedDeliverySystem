package lt.vu.ads.models.order.json;

import lombok.Getter;
import lombok.Setter;
import lt.vu.ads.models.address.Address;
import lt.vu.ads.models.order.Order;
import lt.vu.ads.models.user.User;

@Getter
@Setter
public class OrderCreateView {

    private Address sourceAddress;
    private Address destinationAddress;
    private User destinationUser;
    private int orderCode;

    public static Order of(OrderCreateView orderCreateView) {
        return Order.builder()
                .sourceAddress(orderCreateView.getSourceAddress())
                .destinationAddress(orderCreateView.getDestinationAddress())
                .destinationUser(orderCreateView.getDestinationUser())
                .orderCode(orderCreateView.getOrderCode())
                .build();
    }
}
