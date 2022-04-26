package lt.vu.ads.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
public class Order {

    private Long id;

    private int orderCode;

    private OrderStatus orderStatus;

    private boolean isExpress;

    private boolean isFragile;

    private Size size;

    private double price;

    private Date date;

    private User sourceUser;

    private Address sourceAddress;

    private User destinationUser;

    private Address destinationAddress;

    private Date estimatedArrivalTime;

    private Date convenientArrivalTimeFrom;

    private Date convenientArrivalTimeTo;

    private Courier courier;




}
