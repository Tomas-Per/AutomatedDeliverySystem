package lt.vu.ads.service.order;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.constants.PriceConstants;
import lt.vu.ads.models.Address;
import lt.vu.ads.models.Size;
import lt.vu.ads.service.order.utils.DistanceCalculator;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
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

    public Date calculateArrivalTime(boolean isExpress) {
        Calendar calendar = Calendar.getInstance();

        if(isExpress) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            return calendar.getTime();
        }
        calendar.add(Calendar.DAY_OF_YEAR, 5);
        return calendar.getTime();

    }
}
