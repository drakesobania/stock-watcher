package models;

import java.util.*;

public class StockStore {
    private HashMap<Integer, Stock> stocks = new HashMap<>();

    public Optional<Stock> add(Stock stock) {
        stocks.put(stocks.size(), stock);
        return Optional.ofNullable(stock);
    }

    public boolean remove(Stock stock) {
        for (Integer key: stocks.keySet()) {
            if (stocks.get(key).getSymbol().equals(stock.getSymbol())) {
                stocks.remove(key);
                return true;
            }
        }
        return true;
    }
}
