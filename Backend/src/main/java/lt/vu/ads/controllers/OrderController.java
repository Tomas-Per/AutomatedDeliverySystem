package lt.vu.ads.controllers;

import lt.vu.ads.exceptions.ResourceNotFoundException;
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
            throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
        return ResponseEntity.ok().body(order);
    }

    @PostMapping("/orders")
    public Order createOrder(@RequestBody Order order)
    {
        return orderRepository.save(order);
    }


}
