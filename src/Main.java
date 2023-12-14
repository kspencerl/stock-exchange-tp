import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Instancia o mercado de ações
        StockMarket B3 = StockMarket.getInstance();

        // Cria ação de "Shulambs"
        Stock SHUL4 = new Stock("Shulambs", "SHUL4", "Uma empresa valiosa");

        // Adiciona a ação ao mercado de ações
        B3.addStock(SHUL4);

        // Cria dois corretores, Modal Mais e XP
        Broker modalMais = new Broker("Modal Mais");
        Broker xp = new Broker("XP");

        // Adiciona os corretores como observadores para receberem atualizações sobre as ações
        B3.addObserver(modalMais);
        B3.addObserver(xp);

        // Modal Mais coloca uma ordem de venda de 150 ações da SHUL4 a R$8.00 cada
        modalMais.sell(SHUL4, 150, 8.0);

        // XP coloca uma ordem de compra de 200 ações da SHUL4 a R$7.80 cada
        xp.buy(SHUL4, 200, 7.8);
        // Modal Mais coloca uma ordem de compra de 200 ações da SHUL4 a R$9.00 cada
        modalMais.buy(SHUL4, 200, 9.0);
        // Modal Mais coloca uma ordem de compra de 300 ações da SHUL4 a R$10.50 cada
        modalMais.buy(SHUL4, 300, 10.5);

        // Modal Mais coloca outra ordem de venda de 100 ações da SHUL4 a R$9.00 cada
        modalMais.sell(SHUL4, 100, 9.0);
        // XP coloca uma ordem de venda de 250 ações da SHUL4 a R$10.00 cada
        xp.sell(SHUL4,250, 10.0);


        // Imprime todas as transações que foram realizadas após o processamento das ordens
        for (Transactional transaction : SHUL4.getTransactions()) {
            System.out.println("Transação: " + transaction.getQuantity() + " ações a R$" + transaction.getPrice());
        }

        // Imprime todas as transações feitas em data específica
        SHUL4.getInfo("dd/MM/yyyy HH:mm"); // popule com a data desejada
    }
}


