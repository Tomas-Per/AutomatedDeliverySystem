package lt.vu.ads.controllers;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.models.User.json.UserLoggedInView;
import lt.vu.ads.models.User.json.UserLoginView;
import lt.vu.ads.models.User.json.UserRegisterView;
import lt.vu.ads.service.user.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public UserLoggedInView login(@RequestBody UserLoginView loginView) {
        return userService.login(loginView);
    }

    @PostMapping("/register")
    public UserLoggedInView register(@RequestBody UserRegisterView registerView) {
        return userService.register(registerView);
    }

}
