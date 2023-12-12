//* stock market - bolsa de valores
// centraliza informações das ações
//mapa de ações (stocks) + métodos para receber ordens + notificar atualizações + gerar transações 

//!singleton (apenas uma única instância da bolsa de valores - centralização)

import java.util.HashMap;
import java.util.Map;

public class StockMarket implements Observable {
    //singleton
    private static StockMarket instance;

    private Map<String, Stock> stocks;//ações


    private StockMarket(){
        this.stocks = new HashMap<>();
    }

    public static synchronized StockMarket getInstance(){
        if(instance == null){
            instance = new StockMarket();
        }
        return instance;
    }

    public Stock getStock(String stockName) {
        return stocks.get(stockName);
    }

    public void generateTransaction(Stock stock, int quantity, double price) {
        Transactional transaction = TransactionalFactory.createTransaction(quantity, price);
        stock.addTransaction(transaction);
        notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        for (Stock stock : stocks.values()) {
            stock.addObserver(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        for (Stock stock : stocks.values()) {
            stock.removeObserver(observer);
        }
    }

    @Override
    public void notifyObservers() {
        for (Stock stock : stocks.values()) {
            stock.notifyObservers();
        }
    }
}
