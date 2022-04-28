package lt.vu.ads.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@AttributeOverrides({
        @AttributeOverride(name = "email", column = @Column(nullable = false)),
        @AttributeOverride(name = "password", column = @Column(nullable = false)),
})
public class Courier extends Account {

    @Column
    private String workingHours;

    @OneToMany(mappedBy = "courier")
    private List<Order> orders;
}
