package lt.vu.ads.service.order;

import lt.vu.ads.models.Address;
import lt.vu.ads.models.Size;

import java.util.Date;

public interface OrderService {

    double calculatePrice(Address sourceAddress, Address destinationAddress, Size size);

    Date calculateArrivalTime(boolean isExpress);
}
