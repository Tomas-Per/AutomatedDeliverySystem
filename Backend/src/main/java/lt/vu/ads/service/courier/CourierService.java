package lt.vu.ads.service.courier;

import lt.vu.ads.models.courier.json.CourierLoggedInView;
import lt.vu.ads.models.courier.json.CourierLoginView;
import lt.vu.ads.models.courier.json.CourierRegisterView;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CourierService {

    CourierLoggedInView login(CourierLoginView loginView);

    CourierLoggedInView register(CourierRegisterView registerView);
}