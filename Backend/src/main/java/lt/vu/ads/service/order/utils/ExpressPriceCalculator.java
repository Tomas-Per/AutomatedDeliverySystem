package lt.vu.ads.service.order.utils;


import lt.vu.ads.constants.PriceConstants;
import lt.vu.ads.models.EnumsOrder.Size;

public class ExpressPriceCalculator extends DefaultPriceCalculator {

    public double calculatePrice(double distance, Size size) {
        return super.calculatePrice(distance, size) + PriceConstants.EXPRESS_PRICE_ADDITION;
    }
}
