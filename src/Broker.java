/*CLASSE BROKER - representa uma corretora
 Observa a bolsa de valores (recebe notificações)
 Envia ordens de compra, venda e solicitação de informações*/

public class Broker implements Observer, Runnable {
    //nome da corretora
    private String name;

    /**
     * Construtor para incializar um broker
     * @param name
     */
    public Broker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Envia uma ordem de compra para a ação especificada com a quantidade e preço fornecidos.
     *
     * @param stock    A ação para a qual a ordem de compra está sendo enviada.
     * @param quantidade A quantidade de ações a serem compradas.
     * @param preco O preço pelo qual as ações serão compradas.
     */
    public void buy(Stock stock, int quantity, double price) {
        stock.buy(this, quantity, price);
    }

    /**
     * Envia uma ordem de venda para a ação especificada com a quantidade e preço fornecidos.
     *
     * @param stock    A ação para a qual a ordem de venda está sendo enviada.
     * @param quantidade A quantidade de ações a serem vendidas.
     * @param preco O preço pelo qual as ações serão vendidas.
     */
    public void sell(Stock stock, int quantity, double price) {
        stock.sell(this, quantity, price);
    }

    /**
     * Solicita informações sobre a ação especificada.
     *
     * @param stock A ação para a qual as informações estão sendo solicitadas.
     */
    public void getInfo(Stock stock) { //todo: solicitar informações sobre a ação
    }

    /*Recebe atualizações de determinada ação*/
    @Override
    public void update(Stock stock) {
        System.out.println("Broker " + name + " received update " + stock.getName());
    }

    @Override
    public void run() { 
    }
}
