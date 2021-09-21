package models;

import java.math.BigDecimal;

public class Quote {
    private double price;
    private double change;

    public Quote() {}

    public Quote(double price, double change) {
        this.price = price;
        this.change = change;
    }
}
