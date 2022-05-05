package lt.vu.ads.models.user.json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterView {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}