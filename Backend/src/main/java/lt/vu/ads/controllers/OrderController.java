package lt.vu.ads.controllers;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.exceptions.CustomException;
import lt.vu.ads.models.Address;
import lt.vu.ads.models.Order;
import lt.vu.ads.repositories.AddressRepository;
import lt.vu.ads.repositories.OrderRepository;
import lt.vu.ads.service.order.OrderService;
import lt.vu.ads.service.order.utils.OrderCodeGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final AddressController addressController;
    private final OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    @GetMapping("/order/{id}")
    public ResponseEntity < Order > getOrderById(@PathVariable(value = "id") Long orderId)
    {
        Order order = orderRepository.findOneById(orderId);
        if (order == null){
            throw new CustomException("Order is not found with id: " + orderId);
        }

        return ResponseEntity.ok().body(order);
        }

    @PatchMapping("/order/{id}")
    public ResponseEntity < Order > updateOrder(@PathVariable(value = "id") Long orderId,
                                                @RequestBody Order orderDetails) {
        Order order = orderRepository.findOneById(orderId);
        if (order == null){
            throw new CustomException("Order is not found with id: " + orderId);
        }
        if(orderDetails.getConvenientArrivalTimeTo() == null || orderDetails.getConvenientArrivalTimeFrom() == null){
            throw new CustomException("Order time is empty ");
        }
        if(orderDetails.getConvenientArrivalTimeTo().before(orderDetails.getConvenientArrivalTimeFrom())) {
            throw new CustomException("Order's time is not allowed");
        }
        order.setConvenientArrivalTimeFrom(orderDetails.getConvenientArrivalTimeFrom());
        order.setConvenientArrivalTimeTo(orderDetails.getConvenientArrivalTimeTo());
        final Order updatedOrder = orderRepository.save(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @PostMapping("/orders")
    public Order createOrder(@RequestBody Order order)
    {
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

        OrderCodeGenerator generator = new OrderCodeGenerator();
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
    }


}
