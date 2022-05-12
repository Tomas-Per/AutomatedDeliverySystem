package lt.vu.ads.models.user.json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginView {
    private String email;
    private String password;
}