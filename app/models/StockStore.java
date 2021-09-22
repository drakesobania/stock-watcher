package models;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class StockStore {
    private Set<Stock> stocks = new HashSet<>();
    private final File file = new File("stocks.txt");

    private void read() {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                stocks = (Set<Stock>) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // file is not there, just keep going
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(stocks);
            }
        } catch (FileNotFoundException e) {
            // file is not there, just keep going
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<Stock> add(Stock stock) {
        read();
        stocks.add(stock);
        write();
        return Optional.ofNullable(stock);
    }

    public boolean remove(Stock stock) {
        read();
        boolean deleted = stocks.remove(stock);
        write();
        return deleted;
    }

    public List<Stock> getAll() {
        read();
        return stocks
                .stream()
                .collect(Collectors.toList());
    }
}
