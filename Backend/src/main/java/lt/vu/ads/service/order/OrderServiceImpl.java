package lt.vu.ads.service.order;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.exceptions.CustomException;
import lt.vu.ads.models.address.Address;
import lt.vu.ads.models.order.Order;
import lt.vu.ads.models.order.json.OrderView;
import lt.vu.ads.repositories.AddressRepository;
import lt.vu.ads.repositories.OrderRepository;
import lt.vu.ads.service.NumberGenerator;
import lt.vu.ads.service.address.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final AddressService addressService;

    @Override
    public List<OrderView> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public ResponseEntity<Order> getOrderById(Long orderId) {
        Order order = orderRepository.findOneById(orderId);
        if (order == null) {
            throw new CustomException("Order is not found with id: " + orderId);
        }

        return ResponseEntity.ok().body(order);
    }

    @Override
    public ResponseEntity<Order> updateOrder(Long orderId, Order orderDetails) {
        Order order = orderRepository.findOneById(orderId);
        if (order == null) {
            throw new CustomException("Order is not found with id: " + orderId);
        }
        if (orderDetails.getConvenientArrivalTimeTo() == null || orderDetails.getConvenientArrivalTimeFrom() == null) {
            throw new CustomException("Order time is empty ");//TODO: exception
        }
        if (orderDetails.getConvenientArrivalTimeTo().before(orderDetails.getConvenientArrivalTimeFrom())) {
            throw new CustomException("Order's time is not allowed");//TODO: exception
        }
        order.setConvenientArrivalTimeFrom(orderDetails.getConvenientArrivalTimeFrom());
        order.setConvenientArrivalTimeTo(orderDetails.getConvenientArrivalTimeTo());
        final Order updatedOrder = orderRepository.save(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @Override
    public Order saveOrder(Order order) {
        if (order.getDestinationAddress() == null || order.getSourceAddress() == null) {
            throw new CustomException("Order's source or destinations addresses are empty");//TODO: exception
        }

        if (order.getDestinationAddress() == order.getSourceAddress()) {
            throw new CustomException("Order's source and destinations addresses are not allowed");//TODO: exception
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

        NumberGenerator generator = new NumberGenerator();
        order.setOrderCode(generator.generateNumber());

        if (destination_address != null) {
            order.setDestinationAddress(addressRepository.findOneById(destination_address.getId()));
        } else {
            addressRepository.save(order.getDestinationAddress());
        }

        if (source_address != null) {
            order.setSourceAddress(addressRepository.findOneById(source_address.getId()));
        } else {
            addressRepository.save(order.getSourceAddress());
        }

        return orderRepository.save(order);
    }
}