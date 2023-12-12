//* stock - ação
//representa uma ação na bolsa
//contém informações como nome, código dessa ação específica
//livro de ofertas (OfferBook) para essa ação específica.


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Stock implements Observable{

    private String name;

    private List<Order> offerBook; //livro de ofertas (OfferBook) para essa ação específica.

    private List<Transactional> transactions;

    private Set<Observer> observers;

    public Stock(String name) {
        this.name = name;
        this.offerBook = new LinkedList<>();
        this.transactions = new LinkedList<>();
        this.observers = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public List<Order> getOfferBook(){
        return offerBook;
    }

    public void addOffer(Order order) {
        offerBook.add(order);
        notifyObservers();
    }

    public void addTransaction(Transactional transaction) {
        transactions.add(transaction);
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
            observer.notify(this);
        }
    }
   
}
