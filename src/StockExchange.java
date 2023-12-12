public class StockExchange {
    //singleton
    private static StockExchange instance;

    private StockExchange(){

    }

    public static StockExchange getInstance(){
        if(instance == null){
            instance = new StockExchange();
        }
        return instance;
    }
    
}
