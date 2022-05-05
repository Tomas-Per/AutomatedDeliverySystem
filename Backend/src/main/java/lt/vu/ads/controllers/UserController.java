package lt.vu.ads.controllers;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.models.user.json.UserLoggedInView;
import lt.vu.ads.models.user.json.UserLoginView;
import lt.vu.ads.models.user.json.UserRegisterView;
import lt.vu.ads.service.user.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
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
