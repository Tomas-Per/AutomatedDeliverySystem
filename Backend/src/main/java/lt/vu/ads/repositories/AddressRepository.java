package lt.vu.ads.repositories;

import lt.vu.ads.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByCityAndStreetAndHouseNumberAndCountryAndPostalCode(@Param("city")String city,
                                @Param("street") String street,
                                @Param("houseNumber") String houseNumber,
                                @Param("country") String country,
                                @Param("postalCode") int postalCode);
    Address findOneById(@Param("id") Long id);
}
