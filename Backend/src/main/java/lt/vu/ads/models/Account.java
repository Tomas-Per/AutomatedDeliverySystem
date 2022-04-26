package lt.vu.ads.models;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Account {

    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String password;
}
