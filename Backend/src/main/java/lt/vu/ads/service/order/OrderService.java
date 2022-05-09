package lt.vu.ads.service.order;

import lt.vu.ads.models.Address;
import lt.vu.ads.models.Size;

public interface OrderService {

    double calculatePrice(Address sourceAddress, Address destinationAddress, Size size);
}
