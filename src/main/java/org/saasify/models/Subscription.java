package org.saasify.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Subscription implements Serializable {
    private final Client client;
    private final SubscriptionPlan plan;
    private SubscriptionState state;
    private final LocalDate startDate;
    private LocalDate nextPaymentDate;
    private static final long serialVerisonUID = 1L;

    public Subscription(Client client, SubscriptionPlan plan) {
        if (client == null ||  plan == null) {
            throw new IllegalArgumentException();
        }
        this.client = client;
        this.plan = plan;
        this.state = SubscriptionState.ACTIVE;
        this.startDate = LocalDate.now();
        this.nextPaymentDate = this.startDate.plusMonths(plan.periodMonths());
    }

    public Subscription(Client client, SubscriptionPlan plan, LocalDate startDate, SubscriptionState state, LocalDate nextPaymentDate) {
        if(client == null){
            throw new IllegalArgumentException("Client cannot be null");
        }
        if(plan == null){
            throw new IllegalArgumentException("Plan cannot be null");
        }
        if(state == null){
            throw new IllegalArgumentException("State cannot be null");
        }
        if(startDate == null){
            throw new IllegalArgumentException("StartDate cannot be null");
        }
        if(nextPaymentDate == null){
            throw new IllegalArgumentException("NextPaymentDate cannot be null");
        }

        this.client = client;
        this.plan = plan;
        this.startDate = startDate;
        this.state = state;
        this.nextPaymentDate = nextPaymentDate;
    }

    public Client getClient() {
        return client;
    }

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public SubscriptionState getState() {
        return state;
    }

    public void setState(SubscriptionState state) {
        this.state = state;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(LocalDate nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }
}
