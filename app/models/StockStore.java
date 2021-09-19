package models;

import java.util.*;

public class StockStore {
    private Set<Stock> stocks = new HashSet<>();

    public Optional<Stock> add(Stock stock) {
        stocks.add(stock);
        return Optional.ofNullable(stock);
    }

    public boolean remove(Stock stock) {
        return stocks.remove(stock);
    }
}
