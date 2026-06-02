package org.saasify.models;

import java.io.Serializable;
import java.time.LocalDate;

public record SubscriptionData(String clientDni,int planId,LocalDate startDate,
       LocalDate nextBillingDate,SubscriptionState state) implements Serializable {
    private static final long serialVersionUID = 1L;

    public SubscriptionData(Subscription sub) {
        this(sub.getClient().getDni(),sub.getPlan().id(),sub.getStartDate(),
                sub.getNextPaymentDate(),sub.getState());
    }
}
