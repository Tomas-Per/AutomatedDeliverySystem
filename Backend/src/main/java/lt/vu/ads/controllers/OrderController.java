package lt.vu.ads.controllers;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.models.order.json.OrderCreateView;
import lt.vu.ads.models.order.json.OrderEditView;
import lt.vu.ads.models.order.json.OrderListView;
import lt.vu.ads.models.order.json.OrderView;
import lt.vu.ads.models.user.json.UserEmailView;
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
    public Order createOrder(@RequestBody Order order)
    {
        String generatedCode = "";
        if(order.getDestinationAddress() == null || order.getSourceAddress() == null){
            throw new CustomException("Order's source or destinations addresses are empty");
        }

        if (order.getDestinationAddress() == order.getSourceAddress()){
            throw new CustomException("Order's source and destinations addresses are not allowed");
        }
        Address destination_address = addressRepository.findByCityAndStreetAndHouseNumberAndCountryAndPostalCode(
                order.getDestinationAddress().getCity(),
                order.getDestinationAddress().getStreet(),
                order.getDestinationAddress().getHouseNumber(),
                order.getDestinationAddress().getCountry(),
                order.getDestinationAddress().getPostalCode()
        );
        Address source_address = addressRepository.findByCityAndStreetAndHouseNumberAndCountryAndPostalCode(
                order.getSourceAddress().getCity(),
                order.getSourceAddress().getStreet(),
                order.getSourceAddress().getHouseNumber(),
                order.getSourceAddress().getCountry(),
                order.getSourceAddress().getPostalCode()
        );


        //for order Code duplicates
        OrderCodeGenerator generator = new OrderCodeGenerator();
        Order orderByCode = orderRepository.findByOrderCode(generator.generateOrderCode());

        while(orderByCode != null){
            generatedCode = generator.generateOrderCode();
            orderByCode = orderRepository.findByOrderCode(generatedCode);
        }

        order.setOrderCode(generator.generateOrderCode());

        if (destination_address != null){
            order.setDestinationAddress(addressRepository.findOneById(destination_address.getId()));
        }
        else{
            addressController.createAddress(order.getDestinationAddress());
        }

        if (source_address != null){
            order.setSourceAddress(addressRepository.findOneById(source_address.getId()));
        }
        else{
            addressController.createAddress(order.getSourceAddress());
        }

        Date date = new Date();
        order.setDate(date);

        order.setEstimatedArrivalTime(orderService.calculateArrivalTime(order.getIsExpress()));
        if (order.getSize() == null){
            throw new CustomException("Box size is null");
        }
        order.setPrice(orderService.calculatePrice(order.getSourceAddress(), order.getDestinationAddress(),order.getSize()));

        return orderRepository.save(order);
    /*public Long createOrder(@RequestBody OrderCreateView order) {
        return orderService.saveOrder(order);
    }*/
}
