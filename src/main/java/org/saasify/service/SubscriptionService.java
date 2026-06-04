package org.saasify.service;

import org.saasify.exceptions.DuplicateSubscriptionException;
import org.saasify.exceptions.InsufficientFundsException;
import org.saasify.models.Client;
import org.saasify.models.Subscription;
import org.saasify.models.SubscriptionPlan;
import org.saasify.repository.ClientRepository;
import org.saasify.repository.SubscriptionPlanRepository;
import org.saasify.repository.SubscriptionRepository;

public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final ClientRepository clientRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, ClientRepository clientRepository, SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.clientRepository = clientRepository;
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    public void processBilling(String clientDni, int planId){
        if(subscriptionRepository.findActiveSubscription(clientDni, planId).isPresent()){
            throw new DuplicateSubscriptionException("Subscription already exists");
        }

        Client client = clientRepository.findByDni(clientDni).orElseThrow(()->new RuntimeException("Client not found"));
        SubscriptionPlan subsPlan= subscriptionPlanRepository.findById(planId).orElseThrow();

        if(client.getBankBalance().compareTo(subsPlan.price()) < 0){
            throw new InsufficientFundsException("Insufficient Funds");
        }else{
            client.setBankBalance(client.getBankBalance().subtract(subsPlan.price()));
            clientRepository.save(client);
            Subscription subscription = new Subscription(client,subsPlan);
            subscriptionRepository.save(subscription);
        }


    }
}
