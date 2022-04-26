package lt.vu.ads.models;

/**enum which shows what status order has
 *
 * WAITING_FOR_COURIER - order was paid and user is waiting for courier to pick up the order
 * IN_DELIVERY - order travelling from source to destination address
 * ARRIVED - order arrived to the destination address

*/
public enum OrderStatus {

    WAITING_FOR_COURIER,
    IN_DELIVERY,
    ARRIVED,
}
