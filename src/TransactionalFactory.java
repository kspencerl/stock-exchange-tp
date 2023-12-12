public class TransactionalFactory {
    public static Transactional createTransaction(int quantity, double price) {
        return new Transactional(quantity, price);
    }
}