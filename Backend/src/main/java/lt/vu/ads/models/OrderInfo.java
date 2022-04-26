package lt.vu.ads.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderInfo {

    private Long id;

    private Date date;

    private OrderStatus orderStatus;

    private String description;
}
