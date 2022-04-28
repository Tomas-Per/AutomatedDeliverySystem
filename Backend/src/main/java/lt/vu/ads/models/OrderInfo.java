package lt.vu.ads.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idd;

    @Column(nullable = false)
    private Date date;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column
    private String description;

    @ManyToOne
    private Order order;
}
