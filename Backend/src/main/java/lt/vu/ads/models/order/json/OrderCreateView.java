package lt.vu.ads.models.order.json;

import lombok.Getter;
import lombok.Setter;
import lt.vu.ads.models.EnumsOrder.Size;
import lt.vu.ads.models.address.json.AddressView;

import java.util.Date;

@Getter
@Setter
public class OrderCreateView {

    private Boolean isExpress;
    private Boolean isFragile;
    private double price;
    private Date estimatedArrivalTime;
    private Size size;
    private AddressView sourceAddress;
    private AddressView destinationAddress;
    private UserCreateOrderView destinationUser;
    private Long sourceUserId;
    private String orderCode;

    @Getter
    @Setter
    public static class UserCreateOrderView {
        private String firstName;
        private String lastName;
        private String phoneNumber;
    }
}
