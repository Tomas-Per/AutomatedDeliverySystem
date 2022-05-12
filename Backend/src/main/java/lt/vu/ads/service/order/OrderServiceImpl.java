package lt.vu.ads.service.order;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.constants.PriceConstants;
import lt.vu.ads.exceptions.BadRequestException;
import lt.vu.ads.exceptions.NotFoundException;
import lt.vu.ads.models.Address;
import lt.vu.ads.models.Size;
import lt.vu.ads.models.address.Address;
import lt.vu.ads.models.order.Order;
import lt.vu.ads.models.order.json.OrderCreateView;
import lt.vu.ads.models.order.json.OrderEditView;
import lt.vu.ads.models.order.json.OrderListView;
import lt.vu.ads.models.order.json.OrderView;
import lt.vu.ads.models.user.User;
import lt.vu.ads.models.user.json.UserEmailView;
import lt.vu.ads.repositories.AddressRepository;
import lt.vu.ads.repositories.OrderRepository;
import lt.vu.ads.repositories.UserRepository;
import lt.vu.ads.service.NumberGenerator;
import lt.vu.ads.service.order.utils.DistanceCalculator;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public List<OrderListView> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderListView::of).collect(Collectors.toList());
    }

    @Override
    public OrderView getOrderById(Long orderId) {
        Order order = orderRepository.findOneById(orderId);
        if (order == null) {
            throw new NotFoundException("Order is not found with id: " + orderId);
        }

        return OrderView.of(order);
    }

    @Override
    public List<OrderListView> getOrdersByEmail(UserEmailView emailView) {
        User user = userRepository.findByEmail(emailView.getEmail());
        if (user == null) {
            throw new NotFoundException("");
        }
        List<Order> orders = orderRepository.findAllBySourceUser(user);
        if (orders == null) {
            throw new NotFoundException("Order is not found with id: " + emailView);
        }

        return orders.stream().map(OrderListView::of).collect(Collectors.toList());
    }

    @Override
    public OrderView updateOrder(Long orderId, OrderEditView orderDetails) {
        Order order = orderRepository.findOneById(orderId);
        if (order == null) {
            throw new NotFoundException("Order is not found with id: " + orderId);
        }
        if (orderDetails.getConvenientArrivalTimeTo() == null || orderDetails.getConvenientArrivalTimeFrom() == null) {
            throw new BadRequestException("Order time is empty ");
        }
        if (orderDetails.getConvenientArrivalTimeTo().before(orderDetails.getConvenientArrivalTimeFrom())) {
            throw new BadRequestException("Order's time is not allowed");
        }
        order.setConvenientArrivalTimeFrom(orderDetails.getConvenientArrivalTimeFrom());
        order.setConvenientArrivalTimeTo(orderDetails.getConvenientArrivalTimeTo());
        return OrderView.of(orderRepository.save(order));
    }

    @Override
    public Long saveOrder(OrderCreateView order) {
        if (order.getDestinationAddress() == null || order.getSourceAddress() == null) {
            throw new BadRequestException("Order's source or destinations addresses are empty");
        }

        if (order.getDestinationAddress() == order.getSourceAddress()) {
            throw new BadRequestException("Order's source and destinations addresses are not allowed");
        }
        Address destinationAddress = addressRepository.findByCityAndStreetAndHouseNumberAndCountryAndPostalCode(
                order.getDestinationAddress().getCity(),
                order.getDestinationAddress().getStreet(),
                order.getDestinationAddress().getHouseNumber(),
                order.getDestinationAddress().getCountry(),
                order.getDestinationAddress().getPostalCode()
        );
        Address sourceAddress = addressRepository.findByCityAndStreetAndHouseNumberAndCountryAndPostalCode(
                order.getSourceAddress().getCity(),
                order.getSourceAddress().getStreet(),
                order.getSourceAddress().getHouseNumber(),
                order.getSourceAddress().getCountry(),
                order.getSourceAddress().getPostalCode()
        );

        NumberGenerator generator = new NumberGenerator();
        order.setOrderCode(generator.generateNumber());

        if (destinationAddress != null) {
            order.setDestinationAddress(addressRepository.findOneById(destinationAddress.getId()));
        } else {
            addressRepository.save(order.getDestinationAddress());
        }

        if (sourceAddress != null) {
            order.setSourceAddress(addressRepository.findOneById(sourceAddress.getId()));
        } else {
            addressRepository.save(order.getSourceAddress());
        }

        return orderRepository.save(OrderCreateView.of(order)).getId();
    }


    public double calculatePrice(Address sourceAddress, Address destinationAddress, Size size) {

        DistanceCalculator distanceCalculator = new DistanceCalculator();

        double distance = distanceCalculator.calculateDistance(sourceAddress, destinationAddress);
        double price = distance * PriceConstants.PRICE_PER_KM;

        switch (size) {
            case S:
                price += PriceConstants.S_SIZE_PRICE;
            case M:
                price += PriceConstants.M_SIZE_PRICE;
            case L:
                price += PriceConstants.L_SIZE_PRICE;
        }
        return price;
    }

    public Date calculateArrivalTime(boolean isExpress) {
        Calendar calendar = Calendar.getInstance();

        if(isExpress) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            return calendar.getTime();
        }
        calendar.add(Calendar.DAY_OF_YEAR, 5);
        return calendar.getTime();

    }
}