package lt.vu.ads.models.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.vu.ads.models.account.Account;
import lt.vu.ads.models.order.Order;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Table
@Entity
@NoArgsConstructor
public class User extends Account {

    @OneToMany(mappedBy = "sourceUser")
    private List<Order> ordersSent;

    @OneToMany(mappedBy = "destinationUser")
    private List<Order> ordersToGet;

    @Builder
    public User(Long id, String firstName, String lastName, String phoneNumber, String email, String password, List<Order> ordersSent, List<Order> ordersToGet) {
        super(id, firstName, lastName, phoneNumber, email, password);
        this.ordersSent = ordersSent;
        this.ordersToGet = ordersToGet;
    }
}
