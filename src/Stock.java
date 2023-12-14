/* CLASSE STOCK
representa uma ação na bolsa de valores
contém informações como nome, código dessa ação específica
livro de ofertas (OfferBook) para ação específica.*/

import java.util.*;
import java.util.stream.Collectors;

public class Stock implements Observable {
    private String name;
    private String code;
    private String description;
    private List<Order> offerBook; // OfferBook possui ordens de compra e de venda
    private List<Transactional> transactions;
    private Set<Observer> observers;

     /**
     * Construtor para a instanciação de uma ação
     *
     * @param name nome da ação
     * @param code código da ação
     * @param description descrição geral da ação
     * É instanciado uma lista de transações do tipo LinkedList e uma lista de observadores do tipo HashSet
     */
    public Stock(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.offerBook = new LinkedList<>();
        this.transactions = new LinkedList<>();
        this.observers = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public List<Transactional> getTransactions() {
        return transactions;
    }

    /*
    Adiciona transação e notifica observadores
    */
    public void addTransaction(Transactional transaction) {
        transactions.add(transaction);
        notifyObservers();
    }

    /*
    Cria ordem de pedido de compra
    */
    public synchronized void buy(Broker broker, int quantity, double price) {
        Order buyOrder = OrderFactory.createOrder(quantity, price, broker, true);
        processOrder(buyOrder);
    }

    /*
    Cria ordem de pedido de venda
    */
    public synchronized void sell(Broker broker, int quantity, double price) {
        Order sellOrder = OrderFactory.createOrder(quantity, price, broker, false);
        processOrder(sellOrder);
    }

    /*
    Realiza processamento dos pedidos de ordem
    */
    private void processOrder(Order newOrder) {
        boolean isOrderMatched = false;

        // Checa se a nova ordem é igual a alguma existente em offerBook
        for (Order existingOrder : offerBook) {
            if (newOrder.isBuyOrder() == existingOrder.isBuyOrder() &&
                    newOrder.getPrice() == existingOrder.getPrice()) {
                // se ordens iguais, some as quantidades
                existingOrder.setQuantity(existingOrder.getQuantity() + newOrder.getQuantity());
                isOrderMatched = true;
                break;
            }
        }

        if (!isOrderMatched) {
            // se ordem não gera transação, é adiconada ao offerBook
            offerBook.add(newOrder);
        }

        // Após atualizar offerBook, executar transações
        matchOrders();

        // Organiza offerBook: ordens de compra decrescentes por preço, ordens de venda em preço crescente
        sortOfferBook();

        notifyObservers();
    }

    /*
    Executa transações
    */
    private void matchOrders() {
        List<Order> matchedOrders = new LinkedList<>();

        for (Order buyOrder : offerBook.stream().filter(Order::isBuyOrder).collect(Collectors.toList())) {
            for (Order sellOrder : offerBook.stream().filter(o -> !o.isBuyOrder()).collect(Collectors.toList())) {
                if (buyOrder.getPrice() >= sellOrder.getPrice()) {
                    int matchedQuantity = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());
                    double transactionPrice = sellOrder.getPrice();
                    transactions.add(new Transactional(matchedQuantity, transactionPrice));

                    buyOrder.setQuantity(buyOrder.getQuantity() - matchedQuantity);
                    sellOrder.setQuantity(sellOrder.getQuantity() - matchedQuantity);

                    if (buyOrder.getQuantity() == 0) matchedOrders.add(buyOrder);
                    if (sellOrder.getQuantity() == 0) matchedOrders.add(sellOrder);
                }
            }
        }

        // Remove orders de quantidade 0
        offerBook.removeAll(matchedOrders);
    }

    private void sortOfferBook() {
        offerBook.sort((o1, o2) -> {
            if (o1.isBuyOrder() == o2.isBuyOrder()) {
                return o1.isBuyOrder() ? Double.compare(o2.getPrice(), o1.getPrice()) : Double.compare(o1.getPrice(), o2.getPrice());
            }
            return 0;
        });
    }

    
    /**
     * Adiciona um observador para receber notificações sobre atualizações na ação.
     *
     * @param observer O observador a ser adicionado.
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Remove um observador previamente adicionado.
     *
     * @param observer O observador a ser removido.
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifica todos os observadores registrados sobre uma atualização na ação.
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
