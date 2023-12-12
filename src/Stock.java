//ação
//representa uma ação na bolsa
//contém informações como nome, código e um livro de ofertas (OfferBook) para essa ação específica.

import java.util.List;

public class Stock {

    private String name;

    private String code;

    private List<OfferBook> offerBook;

    /// construtor ---- offerbook


    public List<OfferBook> getOfferBook(){
        return offerBook;
    }




    
}
