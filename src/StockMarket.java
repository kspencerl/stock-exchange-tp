/* CLASSE STOCK MARKET
centraliza informações das ações (singleton)
mapa de ações (stocks) + métodos para receber ordens + notificar atualizações + gerar transações */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockMarket implements Observable {
    //singleton
    private static StockMarket instance = new StockMarket();

    private Map<String, Stock> stocks;


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

    public void addStock(Stock stock) {
        stocks.put(stock.getName(), stock);
    }

    public void generateTransaction(Stock stock, int quantity, double price) {
        Transactional transaction = TransactionalFactory.createTransaction(quantity, price);
        stock.addTransaction(transaction);
        notifyObservers();
    }

    /**
     * Adiciona um observador para receber notificações sobre atualizações em todas as ações do mercado.
     *
     * @param observer O observador a ser adicionado.
     */
    @Override
    public void addObserver(Observer observer) {
        for (Stock stock : stocks.values()) {
            stock.addObserver(observer);
        }
    }

    /**
     * Remove um observador previamente adicionado de todas as ações do mercado.
     *
     * @param observer O observador a ser removido.
     */
    @Override
    public void removeObserver(Observer observer) {
        for (Stock stock : stocks.values()) {
            stock.removeObserver(observer);
        }
    }

    /**
     * Notifica todos os observadores registrados sobre uma atualização em todas as ações do mercado.
     */
    @Override
    public void notifyObservers() {
        for (Stock stock : stocks.values()) {
            stock.notifyObservers();
        }
    }
}
