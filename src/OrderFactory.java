public class OrderFactory {
    /* 
    Realiza construção de uma Ordem
    */
    public static Order createOrder(int quantity, double price, Broker broker, boolean isBuyOrder) {
        return new Order(quantity, price, broker, isBuyOrder);
    }
}
