//* order - ordem 
//representa ordem de compra ou de venda
//contém informações de quantidade, valor e corretora

public abstract class Order {
    private int quantity;
    private double price;
    private Broker broker;

    public Order(int quantity, double price, Broker broker) {
        this.quantity = quantity;
        this.price = price;
        this.broker = broker;
    }
}