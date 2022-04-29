package lt.vu.ads.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Table(name = "`order`")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int orderCode;

    @Column
    private boolean isExpress = false;

    @Column
    private boolean isFragile = false;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    private User sourceUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "source_address_id")
    private Address sourceAddress;

    @ManyToOne
    private User destinationUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_address_id")
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
