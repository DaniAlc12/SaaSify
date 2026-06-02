package org.saasify.models;

import java.io.Serializable;
import java.math.BigDecimal;

public record SubscriptionPlan
        (int id,String name,BigDecimal price,
         int periodMonths) implements Serializable {

    private static final long serialVersionUID = 1L;

    public SubscriptionPlan {
        if (id<=0){
            throw new IllegalArgumentException("The id must be a positive integer");
        }
        if (name==null ||  name.isBlank()){
            throw new IllegalArgumentException("The name may not be null or empty");
        }
        if (price==null){
            throw new IllegalArgumentException("The price may not be null");
        }
        if (periodMonths<=0){
            throw new IllegalArgumentException("The period months may not be null or empty");
        }
    }
}
