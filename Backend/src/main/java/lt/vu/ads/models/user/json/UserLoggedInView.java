package lt.vu.ads.models.user.json;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lt.vu.ads.models.user.User;

@Getter
@Setter
@Builder
public class UserLoggedInView {
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public static UserLoggedInView of(User user) {
        return UserLoggedInView.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}