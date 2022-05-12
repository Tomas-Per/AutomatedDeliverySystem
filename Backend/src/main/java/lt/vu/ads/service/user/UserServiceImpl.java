package lt.vu.ads.service.user;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.exceptions.AlreadyExistsException;
import lt.vu.ads.exceptions.NotFoundException;
import lt.vu.ads.exceptions.WrongPasswordException;
import lt.vu.ads.models.user.User;
import lt.vu.ads.models.user.json.UserLoggedInView;
import lt.vu.ads.models.user.json.UserLoginView;
import lt.vu.ads.models.user.json.UserRegisterView;
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
        User user = userRepository.findByEmail(loginView.getEmail());

        if (user == null) {
            throw new NotFoundException("User with such email does not exist");
        }
        if (!passwordEncoder.matches(loginView.getPassword(), user.getPassword())) {
            throw new WrongPasswordException("Wrong password");
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
                .password(passwordEncoder.encode(registerView.getPassword()))
                .build();
        userRepository.save(user);
        return UserLoggedInView.of(user);
    }
}