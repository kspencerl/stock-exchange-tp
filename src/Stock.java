//* stock - ação
//representa uma ação na bolsa
//contém informações como nome, código dessa ação específica
//livro de ofertas (OfferBook) para essa ação específica.


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import java.util.*;

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
        List<Order> matchedOrders = new LinkedList<>();
        Iterator<Order> iterator = offerBook.iterator();

        while (iterator.hasNext()) {
            Order existingOrder = iterator.next();
            // Check if the existing order is a match: buy meets sell at an acceptable price
            if (newOrder.isBuyOrder() != existingOrder.isBuyOrder() &&
                    ((newOrder.isBuyOrder() && newOrder.getPrice() >= existingOrder.getPrice()) ||
                            (!newOrder.isBuyOrder() && newOrder.getPrice() <= existingOrder.getPrice()))) {

                int matchedQuantity = Math.min(newOrder.getQuantity(), existingOrder.getQuantity());
                double transactionPrice = existingOrder.getPrice(); // Transaction happens at the listed price
                transactions.add(new Transactional(matchedQuantity, transactionPrice));

                newOrder.setQuantity(newOrder.getQuantity() - matchedQuantity);
                existingOrder.setQuantity(existingOrder.getQuantity() - matchedQuantity);

                if (existingOrder.getQuantity() == 0) {
                    matchedOrders.add(existingOrder);
                }

                if (newOrder.getQuantity() == 0) {
                    break;
                }
            }
        }

        // Remove all matched orders from offer book
        offerBook.removeAll(matchedOrders);

        // If there's remaining quantity in the new order, add it to the offer book
        if (newOrder.getQuantity() > 0) {
            offerBook.add(newOrder);
        }

        // Sort the offer book: buy orders descending by price, sell orders ascending by price
        offerBook.sort((o1, o2) -> {
            if (o1.isBuyOrder() == o2.isBuyOrder()) {
                return o1.isBuyOrder() ? Double.compare(o2.getPrice(), o1.getPrice()) : Double.compare(o1.getPrice(), o2.getPrice());
            }
            return 0; // Don't sort buy orders with respect to sell orders
        });

        notifyObservers();
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
