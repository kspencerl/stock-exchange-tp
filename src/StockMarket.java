//bolsa de valores
// centraliza informações das ações
//mapa de ações (stocks) + métodos para receber ordens + notificar atualizações + gerar transações 

//!singleton (apenas uma única instância da bolsa de valores - centralização)
public class StockMarket {
    //singleton
    private static StockMarket instance;

    private StockMarket(){

    }

    public static StockMarket getInstance(){
        if(instance == null){
            instance = new StockMarket();
        }
        return instance;
    }
    
}
