package lt.vu.ads.service.address;

import lt.vu.ads.models.address.json.AddressCreateView;
import lt.vu.ads.models.address.json.AddressFromDetailsView;
import lt.vu.ads.models.address.json.AddressView;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface AddressService {
    List<AddressView> getAddresses();

    AddressView getAddress(Long addressId);

    AddressView getAddressByOrder(AddressFromDetailsView address);

    Long saveAddress(AddressCreateView address);
}