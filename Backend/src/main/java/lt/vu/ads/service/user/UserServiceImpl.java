package lt.vu.ads.service.user;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.exceptions.CustomException;
import lt.vu.ads.models.User.User;
import lt.vu.ads.models.User.json.UserLoggedInView;
import lt.vu.ads.models.User.json.UserLoginView;
import lt.vu.ads.models.User.json.UserRegisterView;
import lt.vu.ads.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserLoggedInView login(UserLoginView loginView) {
        User user = userRepository.findByEmailAndPassword(loginView.getEmail(), loginView.getPassword());
        return UserLoggedInView.of(user);
    }

    @Override
    public UserLoggedInView register(UserRegisterView registerView) {
        if (userRepository.findByEmail(registerView.getEmail()) != null) {
            throw new CustomException("This email already exists");
        }
        User user = User.builder()
                .firstName(registerView.getFirstName())
                .lastName(registerView.getLastName())
                .phoneNumber(registerView.getPhoneNumber())
                .email(registerView.getEmail())
                .password(registerView.getPassword())
                .build();
        userRepository.save(user);
        return UserLoggedInView.of(user);
    }
}