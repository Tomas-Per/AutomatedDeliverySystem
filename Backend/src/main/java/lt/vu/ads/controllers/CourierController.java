package lt.vu.ads.controllers;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.models.courier.json.CourierLoggedInView;
import lt.vu.ads.models.courier.json.CourierLoginView;
import lt.vu.ads.models.courier.json.CourierRegisterView;
import lt.vu.ads.service.courier.CourierService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courier")
@RequiredArgsConstructor
public class CourierController {
    private final CourierService courierService;

    @PostMapping("/login")
    public CourierLoggedInView login(@RequestBody CourierLoginView loginView) {
        return courierService.login(loginView);
    }

    @PostMapping("/register")
    public CourierLoggedInView register(@RequestBody CourierRegisterView registerView) {
        return courierService.register(registerView);
    }

}
