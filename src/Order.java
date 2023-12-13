public class Order {
    private int quantity;
    private double price;
    private Broker broker;
    private boolean isBuyOrder; // This field indicates if it's a buy order or not

    public Order(int quantity, double price, Broker broker, boolean isBuyOrder) {
        this.quantity = quantity;
        this.price = price;
        this.broker = broker;
        this.isBuyOrder = isBuyOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Broker getBroker() {
        return broker;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    public void setBuyOrder(boolean buyOrder) {
        isBuyOrder = buyOrder;
    }
// Getters and setters for the class fields
    // ...

    public boolean isBuyOrder() {
        return isBuyOrder;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Additional getters and setters if necessary
}
