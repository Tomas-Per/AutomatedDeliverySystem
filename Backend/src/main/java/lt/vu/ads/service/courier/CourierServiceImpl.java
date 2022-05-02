package lt.vu.ads.service.courier;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.exceptions.AlreadyExistsException;
import lt.vu.ads.exceptions.CustomException;
import lt.vu.ads.exceptions.WrongPasswordException;
import lt.vu.ads.models.Courier.Courier;
import lt.vu.ads.models.Courier.json.CourierLoggedInView;
import lt.vu.ads.models.Courier.json.CourierLoginView;
import lt.vu.ads.models.Courier.json.CourierRegisterView;
import lt.vu.ads.repositories.CourierRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {
    private final CourierRepository courierRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public CourierLoggedInView login(CourierLoginView loginView) {
        Courier courier = courierRepository.findByEmail(loginView.getEmail());

        if (courier == null) {
            throw new CustomException("User with such email does not exist");
        }
        if (!passwordEncoder.matches(loginView.getPassword(), courier.getPassword())) {
            throw new WrongPasswordException("Wrong password");
        }

        return CourierLoggedInView.of(courier);
    }

    @Override
    public CourierLoggedInView register(CourierRegisterView registerView) {
        if (courierRepository.findByEmail(registerView.getEmail()) != null) {
            throw new AlreadyExistsException("This email already exists");
        }
        Courier courier = Courier.builder()
                .firstName(registerView.getFirstName())
                .lastName(registerView.getLastName())
                .phoneNumber(registerView.getPhoneNumber())
                .email(registerView.getEmail())
                .password(passwordEncoder.encode(registerView.getPassword()))
                .build();
        courierRepository.save(courier);
        return CourierLoggedInView.of(courier);
    }
}