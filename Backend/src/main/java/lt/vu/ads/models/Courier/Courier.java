package lt.vu.ads.models.Courier;

import lombok.*;
import lt.vu.ads.models.Account;
import lt.vu.ads.models.Order;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "email", column = @Column(nullable = false)),
        @AttributeOverride(name = "password", column = @Column(nullable = false)),
})
public class Courier extends Account {

    @Column
    private String workingHours;

    @OneToMany(mappedBy = "courier")
    private List<Order> orders;

    @Builder
    public Courier(Long id, String firstName, String lastName, String phoneNumber, String email, String password, String workingHours, List<Order> orders) {
        super(id, firstName, lastName, phoneNumber, email, password);
        this.workingHours = workingHours;
        this.orders = orders;
    }
}
