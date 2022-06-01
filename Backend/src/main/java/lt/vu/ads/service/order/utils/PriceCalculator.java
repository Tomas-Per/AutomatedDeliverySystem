package lt.vu.ads.service.order.utils;

import lt.vu.ads.models.EnumsOrder.Size;

public interface PriceCalculator {

    double calculatePrice(double distance, Size size);
}
