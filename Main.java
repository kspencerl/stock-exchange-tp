import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Carregar stocks a partir do arquivo
        loadStocksFromFile("src/util/stockList.txt");

        // Carregar brokers a partir do arquivo
        loadBrokersFromFile("src/util/brokersList.txt");

        // Criação e inicialização das threads para os brokers
        List<Thread> brokerThreads = new ArrayList<>();
        for (Broker broker : brokers) {
            Thread brokerThread = new Thread(broker);
            brokerThreads.add(brokerThread);
            brokerThread.start();
        }

        // Aguarde a conclusão das threads dos brokers
        for (Thread brokerThread : brokerThreads) {
            try {
                brokerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Stock createStock(String stockInfo) {
        String[] parts = stockInfo.split(",");
        String name = parts[0].trim();
        String code = parts[1].trim();
        String description = parts.length > 2 ? parts[2].trim() : "";

        Stock stock = new Stock(name, code, description);

        return stock;
    }

    public static void loadStocksFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Stock stock = createStock(line.trim());
                StockMarket.getInstance().addStock(stock);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadBrokersFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Broker broker = new Broker(line.trim());
                StockMarket.getInstance().addObserver(broker);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
