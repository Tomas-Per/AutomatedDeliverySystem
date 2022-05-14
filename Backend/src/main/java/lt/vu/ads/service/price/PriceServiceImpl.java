package lt.vu.ads.service.price;

import lombok.RequiredArgsConstructor;
import lt.vu.ads.constants.PriceConstants;
import lt.vu.ads.exceptions.BadRequestException;
import lt.vu.ads.models.order.json.OrderCreateView;
import lt.vu.ads.service.order.utils.DistanceCalculator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService{
    public double calculatePrice(OrderCreateView orderView) {

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
        return price;
    }
}
