package lt.vu.ads.controllers;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.models.address.json.AddressCreateView;
import lt.vu.ads.models.address.json.AddressFromDetailsView;
import lt.vu.ads.models.address.json.AddressView;
import lt.vu.ads.service.address.AddressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/address")
    public List<AddressView> getAllAddresses() {
        return addressService.getAddresses();
    }

    @GetMapping("/address/{id}")
    public AddressView getAddressById(@PathVariable(value = "id") Long addressId) {
        return addressService.getAddress(addressId);
    }

    @GetMapping("/address-by-order")
    public AddressView getAddressByOrderDetails(@RequestBody AddressFromDetailsView address) {
        return addressService.getAddressByOrder(address);
    }

    @PostMapping("/address")
    public Long createAddress(@RequestBody AddressCreateView address) {
        return addressService.saveAddress(address);
    }
}
