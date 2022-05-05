package lt.vu.ads.models.address.json;

import lombok.Getter;
import lombok.Setter;
import lt.vu.ads.models.address.Address;

@Getter
@Setter
public class AddressCreateView {
    private String city;
    private String street;
    private String houseNumber;
    private String country;
    private int postalCode;

    public static Address from(AddressCreateView createView) {
        return Address.builder()
                .city(createView.getCity())
                .street(createView.getStreet())
                .houseNumber(createView.getHouseNumber())
                .country(createView.getCountry())
                .postalCode(createView.getPostalCode())
                .build();
    }
}
