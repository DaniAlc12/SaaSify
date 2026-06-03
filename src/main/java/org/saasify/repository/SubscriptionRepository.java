package org.saasify.repository;

import org.saasify.models.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {
    void save(Subscription subscription);
    Optional<Subscription> findByClientDni(String dni);
    List<Subscription> findAll();
    Optional<Subscription> findActiveSubscription(String clientDni, int planId);
}
