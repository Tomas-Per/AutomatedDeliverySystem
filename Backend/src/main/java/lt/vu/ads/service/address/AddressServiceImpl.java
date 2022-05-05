package lt.vu.ads.service.address;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.exceptions.CustomException;
import lt.vu.ads.models.address.Address;
import lt.vu.ads.models.address.json.AddressCreateView;
import lt.vu.ads.models.address.json.AddressFromDetailsView;
import lt.vu.ads.models.address.json.AddressView;
import lt.vu.ads.repositories.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Override
    public List<AddressView> getAddresses() {
        return addressRepository.findAll().stream().map(AddressView::of).collect(Collectors.toList());
    }

    @Override
    public AddressView getAddress(Long addressId) {
        Address address = addressRepository.findOneById(addressId);
        if (address == null) {
            throw new CustomException("Address is not found with id: " + addressId);//TODO: exception
        }
        return AddressView.of(address);
    }

    @Override
    public AddressView getAddressByOrder(AddressFromDetailsView addressView) {
        Address address = addressRepository.findByCityAndStreetAndHouseNumberAndCountryAndPostalCode(
                addressView.getCity(),
                addressView.getStreet(),
                addressView.getHouseNumber(),
                addressView.getCountry(),
                addressView.getPostalCode()
        );
        if (address == null) {
            throw new CustomException("Address is not found in database");
        }
        return AddressView.of(address);
    }

    @Override
    public Long saveAddress(AddressCreateView address) {
        if (address.getStreet() == null ||
                address.getCity() == null ||
                address.getCountry() == null ||
                address.getHouseNumber() == null ||
                address.getPostalCode() == 0
        ) {
            throw new CustomException("Address is null");
        }
        return addressRepository.save(AddressCreateView.from(address)).getId();
    }
}