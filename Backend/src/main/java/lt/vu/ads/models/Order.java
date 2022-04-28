package lt.vu.ads.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private int orderCode;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean isExpress;

    @Column(columnDefinition="BOOLEAN DEFAULT false")
    private boolean isFragile;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    private User sourceUser;

    @Embedded
    private Address sourceAddress;

    @ManyToOne
    private User destinationUser;

    @Embedded
    private Address destinationAddress;

    @Column(nullable = false)
    private Date estimatedArrivalTime;

    @Column
    private Date convenientArrivalTimeFrom;

    @Column
    private Date convenientArrivalTimeTo;

    @ManyToOne
    private Courier courier;

    @OneToMany(mappedBy = "order")
    private List<OrderInfo> orderInfoList;




}
