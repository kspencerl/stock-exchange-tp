//Interface Observable que define métodos para adiconar um observador, remover observador e notificar os observadores*/
public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
