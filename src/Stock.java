//* stock - ação
//representa uma ação na bolsa
//contém informações como nome, código dessa ação específica
//livro de ofertas (OfferBook) para essa ação específica.


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import java.util.*;
import java.util.stream.Collectors;

public class Stock implements Observable {
    private String name;
    private String code;
    private String description;
    private List<Order> offerBook; // OfferBook now holds all orders, both buy and sell
    private List<Transactional> transactions;
    private Set<Observer> observers;

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

    public void setTransactions(List<Transactional> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transactional transaction) {
        transactions.add(transaction);
        notifyObservers();
    }

    public synchronized void buy(Broker broker, int quantity, double price) {
        Order buyOrder = OrderFactory.createOrder(quantity, price, broker, true);
        processOrder(buyOrder);
    }

    public synchronized void sell(Broker broker, int quantity, double price) {
        Order sellOrder = OrderFactory.createOrder(quantity, price, broker, false);
        processOrder(sellOrder);
    }

    private void processOrder(Order newOrder) {
        boolean isOrderMatched = false;

        // Check if the new order matches any existing order in the offer book
        for (Order existingOrder : offerBook) {
            if (newOrder.isBuyOrder() == existingOrder.isBuyOrder() &&
                    newOrder.getPrice() == existingOrder.getPrice()) {
                // If a match is found, sum the quantities
                existingOrder.setQuantity(existingOrder.getQuantity() + newOrder.getQuantity());
                isOrderMatched = true;
                break; // Since we've found the match, we don't need to check further
            }
        }

        if (!isOrderMatched) {
            // If no matching order is found, add the new order to the offer book
            offerBook.add(newOrder);
        }

        // After updating the offer book, match orders to execute transactions
        matchOrders();

        // Sort the offer book: buy orders descending by price, sell orders ascending by price
        sortOfferBook();

        notifyObservers();
    }

    private void matchOrders() {
        List<Order> matchedOrders = new LinkedList<>();
        List<Order> toRemove = new LinkedList<>();

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

        // Remove matched orders from the offer book
        offerBook.removeAll(matchedOrders);
    }

    private void sortOfferBook() {
        offerBook.sort((o1, o2) -> {
            if (o1.isBuyOrder() == o2.isBuyOrder()) {
                return o1.isBuyOrder() ? Double.compare(o2.getPrice(), o1.getPrice()) : Double.compare(o1.getPrice(), o2.getPrice());
            }
            return 0; // Don't sort buy orders with respect to sell orders
        });
    }
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this); // Changed from notify to update to match Observer interface method name
        }
    }
}
