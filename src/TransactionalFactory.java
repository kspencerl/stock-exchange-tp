public class TransactionalFactory {
    /*
    Realiza construção de uma transação
    */
    public static Transactional createTransaction(int quantity, double price) {
        return new Transactional(quantity, price);
    }
}
