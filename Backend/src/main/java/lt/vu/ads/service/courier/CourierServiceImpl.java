package lt.vu.ads.service.courier;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.models.Courier.Courier;
import lt.vu.ads.models.Courier.json.CourierLoggedInView;
import lt.vu.ads.models.Courier.json.CourierLoginView;
import lt.vu.ads.models.Courier.json.CourierRegisterView;
import lt.vu.ads.repository.CourierRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {
    private final CourierRepository courierRepository;

    @Override
    public CourierLoggedInView login(CourierLoginView loginView) {
        Courier courier = courierRepository.findByEmailAndPassword(loginView.getEmail(), loginView.getPassword());
        return CourierLoggedInView.of(courier);
    }

    @Override
    public CourierLoggedInView register(CourierRegisterView registerView) {
        if (courierRepository.findByEmail(registerView.getEmail()) != null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "This email already exists");
        }
        Courier courier = Courier.builder()
                .firstName(registerView.getFirstName())
                .lastName(registerView.getLastName())
                .phoneNumber(registerView.getPhoneNumber())
                .email(registerView.getEmail())
                .password(registerView.getPassword())
                .build();
        return CourierLoggedInView.of(courier);
    }
}