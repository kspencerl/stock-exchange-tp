/* CLASSE ORDER
Representa ordem de compra ou de venda
Contém informações de quantidade, valor e corretora*/
public class Order {
    private int quantity;
    private double price;
    private Broker broker;
    private boolean isBuyOrder;

    /**
     * Construtor de uma nova ordem com a quantidade, preço, corretora e tipo de ordem.
     *
     * @param quantidade A quantidade de ações na ordem.
     * @param preco O preço pelo qual as ações serão compradas ou vendidas.
     * @param corretora A corretora responsável pela ordem.
     * @param isOrdemCompra Indica se é uma ordem de compra (true) ou venda (false).
     */
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

    /**
     * Define se é uma ordem de compra (true) ou venda (false).
     *
     * @param isOrdemCompra Indica se é uma ordem de compra (true) ou venda (false).
     */
    public void setBuyOrder(boolean buyOrder) {
        isBuyOrder = buyOrder;
    }

    /**
     * Verifica se é uma ordem de compra.
     *
     * @return True se for uma ordem de compra, false se for uma ordem de venda.
     */
    public boolean isBuyOrder() {
        return isBuyOrder;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
