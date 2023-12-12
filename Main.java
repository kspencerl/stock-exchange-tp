import stockExchange.Broker;

import java.util.HashMap;
import java.util.Map;

public class Main implements Runnable {

    public static void main(String[] args) {

        // Factory Brokers
        Broker broker = new Broker();

        Map<String, Broker> listBroker = new HashMap<>();

        listBroker.put("XP", broker);

        Thread[] brokers = new Thread[20];

        for(int i = 0; i < 20; i++){
            brokers[i] = new Thread(broker.getName());
        }

        for(int i = 0; i < 20; i++){
            broker.buy();
            brokers[i].start();
        }
    }

    @Override
    public void run() {

    }

}