package lt.vu.ads.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Table
@Entity
public class User extends Account {

    @OneToMany(mappedBy = "sourceUser")
    private List<Order> ordersSent;

    @OneToMany(mappedBy = "destinationUser")
    private List<Order> ordersToGet;
}
