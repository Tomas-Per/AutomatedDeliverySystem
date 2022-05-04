package lt.vu.ads.models.Courier.json;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lt.vu.ads.models.Courier.Courier;

@Getter
@Setter
@Builder
public class CourierLoggedInView {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String workingHours;

    public static CourierLoggedInView of(Courier courier) {
        return CourierLoggedInView.builder()
                .firstName(courier.getFirstName())
                .lastName(courier.getLastName())
                .phoneNumber(courier.getPhoneNumber())
                .workingHours(courier.getWorkingHours())
                .build();
    }
}