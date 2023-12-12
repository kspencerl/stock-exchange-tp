public class OrderFactory {
    public static Order createOrder(int quantity, double price, Broker broker) {
        return new Order(quantity, price, broker) {};
    }
}