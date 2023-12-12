//* transactional - transação
//representa transação realizada na bolsa
//contém informação sobre a quantidade, valor e horário da transação

import java.time.LocalDateTime;

public class Transactional {
    private int quantity;
    private double price;
    private LocalDateTime dateTime;

    public Transactional (int quantity, double price){
        this.quantity = quantity;
        this.price = price;
        this.dateTime = LocalDateTime.now();
    }

    public int getQuantity(){
        return this.quantity;
    }

    public double getPrice(){
        return this.price;
    }

    public LocalDateTime getDateTime(){
        return this.dateTime;
    }

    public double getTotal(){
        return this.quantity * this.price;
    }
    
}
