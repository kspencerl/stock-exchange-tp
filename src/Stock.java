//* stock - ação
//representa uma ação na bolsa
//contém informações como nome, código dessa ação específica
//livro de ofertas (OfferBook) para essa ação específica.

import java.util.List;

public class Stock {

    private String name;

    private String code;

    private List<Order> offerBook; //livro de ofertas (OfferBook) para essa ação específica.

    /// construtor ---- offerbook


    public List<Order> getOfferBook(){
        return offerBook;
    }




    
}
