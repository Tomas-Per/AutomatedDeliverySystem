package lt.vu.ads.models.address.json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressFromDetailsView {
    private String city;
    private String street;
    private String houseNumber;
    private String country;
    private int postalCode;
}
