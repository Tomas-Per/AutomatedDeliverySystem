package lt.vu.ads.models.order.json;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderEditView {
    private Date convenientArrivalTimeFrom;
    private Date convenientArrivalTimeTo;
}
