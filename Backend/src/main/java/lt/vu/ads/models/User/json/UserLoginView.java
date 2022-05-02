package lt.vu.ads.models.User.json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginView {
    private String email;
    private String password;
}