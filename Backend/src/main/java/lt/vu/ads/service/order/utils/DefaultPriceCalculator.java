package lt.vu.ads.service.order.utils;

import lt.vu.ads.constants.PriceConstants;
import lt.vu.ads.models.EnumsOrder.Size;

public class DefaultPriceCalculator implements PriceCalculator {
    

    @Override
    public double calculatePrice(double distance, Size size) {
        double price = distance * PriceConstants.PRICE_PER_KM;

        switch (size) {
            case S:
                price += PriceConstants.S_SIZE_PRICE;
                break;
            case M:
                price += PriceConstants.M_SIZE_PRICE;
                break;
            case L:
                price += PriceConstants.L_SIZE_PRICE;
                break;
        }
        return price;
    }
}
