package lt.vu.ads.models.Courier.json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierRegisterView {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}