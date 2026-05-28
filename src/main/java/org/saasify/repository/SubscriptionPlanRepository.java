package org.saasify.repository;

import org.saasify.models.SubscriptionPlan;

import java.util.List;
import java.util.Optional;

public interface SubscriptionPlanRepository {
    Optional<SubscriptionPlan> findById(int id);
    List<SubscriptionPlan> listAll();
}
