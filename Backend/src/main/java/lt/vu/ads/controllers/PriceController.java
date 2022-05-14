package lt.vu.ads.controllers;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.models.order.json.OrderCreateView;
import lt.vu.ads.service.price.PriceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PriceController {
    private final PriceService priceService;
    @GetMapping("/price")
    public double getPrice(@RequestBody OrderCreateView orderView) {
        return priceService.calculatePrice(orderView);
    }
}
