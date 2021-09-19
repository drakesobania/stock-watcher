package models;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StockStore {
    private Map<Integer, Stock> stocks = new HashMap<>();

    public Optional<Stock> add(Stock stock) {
        int id = stocks.size();
        stock.setId(id);
        stocks.put(id, stock);
        return Optional.ofNullable(stock);
    }
}
