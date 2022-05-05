package lt.vu.ads.models.orderInfo;

import lombok.Getter;
import lombok.Setter;
import lt.vu.ads.models.EnumsOrder.OrderStatus;
import lt.vu.ads.models.order.Order;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Table
@Entity
public class OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column
    private String description;

    @ManyToOne
    private Order order;
}
