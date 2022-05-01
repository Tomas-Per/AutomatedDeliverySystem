package lt.vu.ads.service.user;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.exceptions.AlreadyExistsException;
import lt.vu.ads.exceptions.CustomException;
import lt.vu.ads.exceptions.WrongPasswordException;
import lt.vu.ads.models.User.User;
import lt.vu.ads.models.User.json.UserLoggedInView;
import lt.vu.ads.models.User.json.UserLoginView;
import lt.vu.ads.models.User.json.UserRegisterView;
import lt.vu.ads.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserLoggedInView login(UserLoginView loginView) {
        User user = userRepository.findByEmailAndPassword(loginView.getEmail(), loginView.getPassword());

        if (user == null) {
            throw new CustomException("Tokio vartotojo nėra");
        }
        if (!passwordEncoder.matches(loginView.getPassword(), user.getPassword())) {
            throw new WrongPasswordException("Neteisingas slaptažodis");
        }

        return UserLoggedInView.of(user);
    }

    @Override
    public UserLoggedInView register(UserRegisterView registerView) {
        if (userRepository.findByEmail(registerView.getEmail()) != null) {
            throw new AlreadyExistsException("This email already exists");
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