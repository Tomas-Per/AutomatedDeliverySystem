package lt.vu.ads.models.address.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.vu.ads.models.address.Address;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressView {
    private Long id;
    private String city;
    private String street;
    private String houseNumber;
    private String country;
    private int postalCode;

    public static AddressView of(Address address) {
        return AddressView.builder()
                .id(address.getId())
                .city(address.getCity())
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .build();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressView that = (AddressView) o;
        return city.equals(that.city) &&
                street.equals(that.street) &&
                houseNumber.equals(that.houseNumber) &&
                country.equals(that.country)&&
                postalCode == that.postalCode;
    }
}
