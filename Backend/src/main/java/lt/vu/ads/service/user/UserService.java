package lt.vu.ads.service.user;

import lt.vu.ads.models.user.json.UserLoggedInView;
import lt.vu.ads.models.user.json.UserLoginView;
import lt.vu.ads.models.user.json.UserRegisterView;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserService {

    UserLoggedInView login(UserLoginView loginView);

    UserLoggedInView register(UserRegisterView registerView);
}