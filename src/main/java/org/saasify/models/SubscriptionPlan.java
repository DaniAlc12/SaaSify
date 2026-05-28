package org.saasify.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class SubscriptionPlan implements Serializable {

    private final int id;
    private final String name;
    private final BigDecimal price;
    private final int periodMonths;
    private static final long serialVerisonUID = 1L;

    public SubscriptionPlan(int id, String name, BigDecimal price, int periodMonths) {
        if (id <= 0 ) {
            throw new IllegalArgumentException("Id may not be negative");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nombre may not be null or empty");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Precio may not be null or empty");
        }
        if (periodMonths <= 0) {
            throw new IllegalArgumentException("Periodo may not be negative");
        }

        this.id = id;
        this.name = name;
        this.price = price;
        this.periodMonths = periodMonths;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getPeriodMonths() {
        return periodMonths;
    }

}
