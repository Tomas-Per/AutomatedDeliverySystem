package lt.vu.ads.service.price;

import lt.vu.ads.models.order.json.OrderCreateView;
import org.springframework.validation.annotation.Validated;

@Validated
public interface PriceService {
    double calculatePrice(OrderCreateView orderView);
}
