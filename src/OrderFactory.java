public class OrderFactory {
    public static Order createOrder(int quantity, double price, Broker broker, boolean isBuyOrder) {
        return new Order(quantity, price, broker, isBuyOrder);
    }
}
