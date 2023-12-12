//*broker - corretora
//observa bolsa de valores (recebe notificações) 
//envia ordens de compra, venda e solicitação de informações

//!observer (receber notificações)
//
public class Broker implements Observer, Runnable {

    private String name;

    public Broker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void buy(Stock stock, int quantity, double price) {
        Order buyOrder = OrderFactory.createOrder(quantity, price, this);

    }

    public void sell(Stock stock, int quantity, double price) {
        Order sellOrder = OrderFactory.createOrder(quantity, price, this);

    }

    public void getInfo(Stock stock) { //solicitar informações sobre a ação
    }

    @Override
    public void notify(Stock stock) { //lógica para receber notificações sobre atualizações no livro de ofertas ou transações
        System.out.println("Broker " + name + " received update.");
    }

    @Override
    public void run() { //simular a execução do broker em uma thread
    }
}
