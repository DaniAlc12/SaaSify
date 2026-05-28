package org.saasify.models;

import java.io.Serializable;
import java.time.LocalDate;

public class SubscriptionData implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String clientDni;
    private final int planId;
    private final LocalDate startDate;
    private final LocalDate nextBillingDate;
    private final SubscriptionState state;

    public SubscriptionData(Subscription sub) {
        this.clientDni = sub.getClient().getDni();
        this.planId = sub.getPlan().getId();
        this.startDate = sub.getStartDate();
        this.nextBillingDate = sub.getNextPaymentDate();
        this.state = sub.getState();
    }

    public String getClientDni() {
        return clientDni;
    }

    public int getPlanId() {
        return planId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getNextBillingDate() {
        return nextBillingDate;
    }

    public SubscriptionState getState() {
        return state;
    }
}
