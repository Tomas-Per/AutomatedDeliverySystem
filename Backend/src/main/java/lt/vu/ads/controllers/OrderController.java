package lt.vu.ads.controllers;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.models.order.json.OrderCreateView;
import lt.vu.ads.models.order.json.OrderEditView;
import lt.vu.ads.models.order.json.OrderListView;
import lt.vu.ads.models.order.json.OrderView;
import lt.vu.ads.service.order.OrderService;
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
    public  List<OrderListView> getAllOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/order/{id}")
    public OrderView getOrderById(@PathVariable(value = "id") Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PatchMapping("/order/{id}")
    public OrderView updateOrder(@PathVariable(value = "id") Long orderId,
                                             @RequestBody OrderEditView orderDetails) {
        return orderService.updateOrder(orderId, orderDetails);
    }

    @PostMapping("/orders")
    public Long createOrder(@RequestBody OrderCreateView order) {
        return orderService.saveOrder(order);
    }
}
