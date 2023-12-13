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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void buy(Stock stock, int quantity, double price) {
        stock.buy(this, quantity, price);
    }

    public void sell(Stock stock, int quantity, double price) {
        stock.sell(this, quantity, price);
    }


    public void getInfo(Stock stock) { //solicitar informações sobre a ação
    }

    @Override
    public void update(Stock stock) { //lógica para receber notificações sobre atualizações no livro de ofertas ou transações
        System.out.println("Broker " + name + " received update " + stock.getName());
    }

    @Override
    public void run() { //simular a execução do broker em uma thread
    }
}
