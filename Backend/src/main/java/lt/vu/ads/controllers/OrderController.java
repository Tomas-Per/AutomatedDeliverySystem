package lt.vu.ads.controllers;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.models.order.json.*;
import lt.vu.ads.models.orderInfo.OrderInfo;
import lt.vu.ads.models.orderInfo.json.OrderInfoView;
import lt.vu.ads.models.user.json.UserEmailView;
import lt.vu.ads.service.order.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @GetMapping("/order/byOrderCode/{orderCode}")
    public OrderView getOrderByOrderCode(@PathVariable(value = "orderCode") String orderCode){
        return orderService.getOrderByOrderCode(orderCode);
    }

    @GetMapping("/order/info/{id}")
    public OrderInfoView getOrderInfoById(@PathVariable(value = "id") Long orderId) {
        return orderService.getOrderInfoById(orderId);
    }

    @GetMapping("/order/email")
    public List<OrderListView> getOrderByEmail(@RequestBody UserEmailView emailView) {
        return orderService.getOrdersByEmail(emailView);
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

    @PostMapping("/priceAndDate")
    public OrderPreviewView getPriceAndDate(@RequestBody OrderCreateView orderView) {
        return orderService.calculatePriceAndDate(orderView);
    }

}
