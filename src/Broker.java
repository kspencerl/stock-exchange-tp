//*broker - corretora
//observa bolsa de valores (recebe notificações) 
//envia ordens de compra, venda e solicitação de informações

//!observer (receber notificações)
//

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Broker implements Observer, Runnable {

    private String name;

    public Broker(String name) {
        this.name = name;
    }

    public static void loadBrokersFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Broker broker = new Broker(line.trim());
                StockMarket.getInstance().addObserver(broker); // Adiciona cada corretor como observador da bolsa
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void buy(Stock stock, int quantity, double price) {
        Order buyOrder = OrderFactory.createOrder(quantity, price, this);
        stock.addOrder(buyOrder);
    }

    public void sell(Stock stock, int quantity, double price) {
        Order sellOrder = OrderFactory.createOrder(quantity, price, this);
        stock.addOrder(sellOrder);
    }

    public void getInfo(Stock stock) { //solicitar informações sobre a ação
    }

    @Override
    public void notify(Stock stock) { //lógica para receber notificações sobre atualizações no livro de ofertas ou transações
        System.out.println("Broker " + name + " received update " + stock.getName());
    }

    @Override
    public void run() { //simular a execução do broker em uma thread
    }
}
