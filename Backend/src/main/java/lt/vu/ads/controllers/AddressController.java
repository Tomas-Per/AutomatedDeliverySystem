package lt.vu.ads.controllers;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.exceptions.CustomException;
import lt.vu.ads.models.Address;
import lt.vu.ads.repositories.AddressRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddressController {

    private final AddressRepository addressRepository;

    @GetMapping("/address")
    public List<Address> getAllAddresses(){
        return addressRepository.findAll();
    }

    @GetMapping("/address/{id}")
    public ResponseEntity < Address > getAddressById(@PathVariable(value = "id") Long addressId){
        Address address = addressRepository.findOneById(addressId);
        if (address == null){
            throw new CustomException("Address is not found with id: " + addressId);
        }
        return ResponseEntity.ok().body(address);
    }
    @GetMapping("/address/{city}/{street}/{houseNumber}/{country}/{postalCode}")
    public ResponseEntity<Address> getAddressByOrderDetails(
            @PathVariable(value = "city") String city,
            @PathVariable(value = "street") String street,
            @PathVariable(value = "houseNumber") String houseNumber,
            @PathVariable(value = "country") String country,
            @PathVariable(value = "postalCode") int postalCode)
    {
    Address address = addressRepository.findByCityAndStreetAndHouseNumberAndCountryAndPostalCode(city,street,houseNumber,country,postalCode);
        if (address == null){
            throw new CustomException("Address is not found in database" );
        }
    return ResponseEntity.ok().body(address);
    }

    @PostMapping("/address")
    public Address createAddress(@RequestBody Address address)
    {
            if (address.getStreet() == null ||
                    address.getCity() == null ||
                    address.getCountry() == null ||
                    address.getHouseNumber() == null ||
                    address.getPostalCode() == 0
            ){
                throw new CustomException("Address is null");
            }
        return addressRepository.save(address);
    }
}
