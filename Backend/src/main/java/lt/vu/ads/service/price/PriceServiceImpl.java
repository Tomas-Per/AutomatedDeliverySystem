package lt.vu.ads.service.price;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.constants.PriceConstants;
import lt.vu.ads.models.EnumsOrder.Size;
import lt.vu.ads.models.address.Address;
import lt.vu.ads.service.order.utils.DistanceCalculator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService{
    public double calculatePrice(Address sourceAddress, Address destinationAddress, Size size, Boolean isExpress) {

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

        if(isExpress){
            price += PriceConstants.EXPRESS_PRICE_ADDITION;
        }
        return price;
    }
}
