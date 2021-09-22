package models;

import java.io.Serializable;
import java.util.Objects;

public class Stock implements Serializable {
    private static final long serialVersionUID = 34L;
    private String symbol;
    private double price;
    private double change;
    private Double average;

    public Stock() {}

    public Stock(String symbol) {
        this.setSymbol(symbol);
    }

    public Stock(String symbol, double price, double change, Double average) {
        this.setSymbol(symbol);
        this.setPrice(price);
        this.setChange(change);
        this.setAverage(average);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(getSymbol(), stock.getSymbol());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSymbol());
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }
}
