package lt.vu.ads.controllers;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.models.order.Order;
import lt.vu.ads.service.order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "id") Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PatchMapping("/order/{id}")
    public ResponseEntity<Order> updateOrderd(@PathVariable(value = "id") Long orderId,
                                             @RequestBody Order orderDetails) {
        return orderService.updateOrder(orderId, orderDetails);
    }

    @PostMapping("/orders")
    public Order ecreateOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }
}
