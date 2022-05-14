package lt.vu.ads.service.price;

import lt.vu.ads.models.EnumsOrder.Size;
import lt.vu.ads.models.address.Address;
import org.springframework.validation.annotation.Validated;

@Validated
public interface PriceService {
    double calculatePrice(Address sourceAddress, Address destinationAddress, Size size, Boolean isExpress);
}
