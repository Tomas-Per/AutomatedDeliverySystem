package lt.vu.ads.controllers;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.models.order.Order;
import lt.vu.ads.service.price.PriceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PriceController {
    private final PriceService priceService;
    @GetMapping("/price")
    public double getPrice(@RequestBody Order order) {
        return priceService.calculatePrice(order.getSourceAddress(),
                order.getDestinationAddress(),
                order.getSize(),
                order.getIsExpress());
    }
}
