package org.saasify.models;

import java.io.Serializable;

public enum SubscriptionState implements Serializable {
    ACTIVE,
    NONPAYMENT,
    CANCELLED
}
