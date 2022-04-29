package lt.vu.ads.controllers;

import lt.vu.ads.models.Order;
import lt.vu.ads.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/orders")
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    @GetMapping("/order/{id}")
    public ResponseEntity < Order > getOrderById(@PathVariable(value = "id") Long orderId)
            throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found for this id :: " + orderId));
        return ResponseEntity.ok().body(order);
    }

    @PutMapping("/order/{id}")
    public ResponseEntity < Order > updateOrder(@PathVariable(value = "id") Long orderId,
                                                       @RequestBody Order orderDetails) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found for this id :: " + orderId));

        if(order.getConvenientArrivalTimeTo().before(order.getConvenientArrivalTimeFrom())) {
            throw new Exception("Order's time is not allowed :: ");
        }
        order.setConvenientArrivalTimeFrom(orderDetails.getConvenientArrivalTimeFrom());
        order.setConvenientArrivalTimeTo(orderDetails.getConvenientArrivalTimeTo());
        final Order updatedOrder = orderRepository.save(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @PostMapping("/orders")
    public Order createOrder(@RequestBody Order order)
    {
        return orderRepository.save(order);
    }


}
