package lt.vu.ads.service.order;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.constants.PriceConstants;
import lt.vu.ads.exceptions.BadRequestException;
import lt.vu.ads.exceptions.NotFoundException;
import lt.vu.ads.models.address.Address;
import lt.vu.ads.models.order.Order;
import lt.vu.ads.models.order.json.OrderCreateView;
import lt.vu.ads.models.order.json.OrderEditView;
import lt.vu.ads.models.order.json.OrderListView;
import lt.vu.ads.models.order.json.OrderView;
import lt.vu.ads.models.orderInfo.json.OrderInfoView;
import lt.vu.ads.models.user.User;
import lt.vu.ads.models.user.json.UserEmailView;
import lt.vu.ads.repositories.AddressRepository;
import lt.vu.ads.repositories.OrderRepository;
import lt.vu.ads.repositories.UserRepository;
import lt.vu.ads.service.order.utils.DistanceCalculator;
import lt.vu.ads.service.order.utils.OrderCodeGenerator;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    public OrderInfoView getOrderInfoById(Long orderId) {
        Order order = orderRepository.findOneById(orderId);
        if (order == null) {
            throw new NotFoundException("Order is not found with id: " + orderId);
        }
        if (order.getOrderInfoList() == null || order.getOrderInfoList().isEmpty()) {
            throw new BadRequestException("Order status not available");
        }

        return OrderInfoView.of(order);
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
        String generatedCode;
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

        //for order Code duplicates
        OrderCodeGenerator generator = new OrderCodeGenerator();
        Order orderByCode = orderRepository.findByOrderCode(generator.generateOrderCode());

        while (orderByCode != null) {
            generatedCode = generator.generateOrderCode();
            orderByCode = orderRepository.findByOrderCode(generatedCode);
        }

        Order newOrder = new Order();
        newOrder.setOrderCode(generator.generateOrderCode());

        if (destinationAddress == null) {
            Address newDestinationAddress = Address.builder()
                    .city(order.getDestinationAddress().getCity())
                    .street(order.getDestinationAddress().getStreet())
                    .houseNumber(order.getDestinationAddress().getHouseNumber())
                    .country(order.getDestinationAddress().getCountry())
                    .postalCode(order.getDestinationAddress().getPostalCode())
                    .build();

            destinationAddress = addressRepository.save(newDestinationAddress);
        }
        newOrder.setDestinationAddress(destinationAddress);

        if (sourceAddress == null) {
            Address newSourceAddress = Address.builder()
                    .city(order.getSourceAddress().getCity())
                    .street(order.getSourceAddress().getStreet())
                    .houseNumber(order.getSourceAddress().getHouseNumber())
                    .country(order.getSourceAddress().getCountry())
                    .postalCode(order.getSourceAddress().getPostalCode())
                    .build();

            sourceAddress = addressRepository.save(newSourceAddress);
        }
        newOrder.setSourceAddress(sourceAddress);

        Date date = new Date();
        newOrder.setDate(date);

        if (order.getSourceUserId() == null) {
            throw new BadRequestException("Source user id missing");
        }
        Optional<User> optionalUser = userRepository.findById(order.getSourceUserId());
        optionalUser.ifPresent(newOrder::setSourceUser);

        if (order.getDestinationUser() == null) {
            throw new BadRequestException("Destination user missing");
        }
        User user = userRepository.findByFirstNameAndLastNameAndPhoneNumber(
                order.getDestinationUser().getFirstName(),
                order.getDestinationUser().getLastName(),
                order.getDestinationUser().getPhoneNumber()
        );
        if (user == null) {
            user = new User();
            user.setFirstName(order.getDestinationUser().getFirstName());
            user.setLastName(order.getDestinationUser().getLastName());
            user.setPhoneNumber(order.getDestinationUser().getPhoneNumber());
            user = userRepository.save(user);
        }
        newOrder.setDestinationUser(user);

        newOrder.setEstimatedArrivalTime(calculatePriceAndDate(order).getEstimatedArrivalTime());
        newOrder.setPrice(calculatePriceAndDate(order).getPrice());
        return orderRepository.save(newOrder).getId();
    }

    public Date calculateArrivalTime(Boolean isExpress) {
        Calendar calendar = Calendar.getInstance();

        if (isExpress) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            return calendar.getTime();
        }
        calendar.add(Calendar.DAY_OF_YEAR, 5);
        return calendar.getTime();
    }
    public OrderView calculatePriceAndDate(OrderCreateView orderView) {

        if(orderView.getSourceAddress()== null || orderView.getDestinationAddress() == null){
            throw new BadRequestException("Source or destinations addresses are empty");
        }
        if(orderView.getSourceAddress().equals(orderView.getDestinationAddress())){
            throw new BadRequestException("Source or destinations addresses are not allowed");
        }
        if(orderView.getSize() == null){
            throw new BadRequestException("Box size is null");
        }
        DistanceCalculator distanceCalculator = new DistanceCalculator();

        double distance = distanceCalculator.calculateDistance(orderView.getSourceAddress(), orderView.getDestinationAddress());
        double price = distance * PriceConstants.PRICE_PER_KM;

        switch (orderView.getSize()) {
            case S:
                price += PriceConstants.S_SIZE_PRICE;
            case M:
                price += PriceConstants.M_SIZE_PRICE;
            case L:
                price += PriceConstants.L_SIZE_PRICE;
        }
        if(orderView.getIsExpress()){
            price += PriceConstants.EXPRESS_PRICE_ADDITION;
        }

        Date arrivalDate = calculateArrivalTime(orderView.getIsExpress());

        Order order = new Order();
        order.setPrice(price);
        order.setEstimatedArrivalTime(arrivalDate);
        return OrderView.of(order);
    }
}