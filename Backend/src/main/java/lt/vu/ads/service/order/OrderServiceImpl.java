package lt.vu.ads.service.order;

import lt.vu.ads.constants.PriceConstants;
import lt.vu.ads.models.Address;
import lt.vu.ads.models.Size;
import lt.vu.ads.service.order.utils.DistanceCalculator;


public class OrderServiceImpl implements OrderService {


    public double calculatePrice(Address sourceAddress, Address destinationAddress, Size size) {

        DistanceCalculator distanceCalculator = new DistanceCalculator();

        double distance = distanceCalculator.calculateDistance(sourceAddress, destinationAddress);

        double price = distance * PriceConstants.PRICE_PER_KM;

        switch (size) {
            case S:
                price += PriceConstants.S_SIZE_PRICE;
            case M:
                price += PriceConstants.M_SIZE_PRICE;
            case L:
                price += PriceConstants.L_SIZE_PRICE;
        }

        return price;
    }
}
