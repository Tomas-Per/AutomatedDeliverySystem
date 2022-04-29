package lt.vu.ads.controllers;

import lt.vu.ads.models.Address;
import lt.vu.ads.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    AddressRepository addressRepository;

    @GetMapping("/address")
    public List<Address> getAllAddresses(){
        return addressRepository.findAll();
    }

    @GetMapping("/address/{id}")
    public ResponseEntity < Address > getAddressById(@PathVariable(value = "id") Long addressId){
        Address address = addressRepository.findOneById(addressId);
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
        return ResponseEntity.ok().body(address);
    }

    @PostMapping("/address")
    public Address createAddress(@RequestBody Address address) throws Exception
    {
            if (address == null){
                throw new Exception("Address is null");
            }
        return addressRepository.save(address);
    }
}
