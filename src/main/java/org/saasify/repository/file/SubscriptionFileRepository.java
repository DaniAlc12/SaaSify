package org.saasify.repository.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.saasify.models.*;
import org.saasify.repository.ClientRepository;
import org.saasify.repository.SubscriptionPlanRepository;
import org.saasify.repository.SubscriptionRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubscriptionFileRepository implements SubscriptionRepository {
    private final String filePath;
    private final SubscriptionPlanRepository planRepository;
    private final ClientRepository clientRepository;
    private final List<Subscription> subscriptions;

    public SubscriptionFileRepository(String filePath, SubscriptionPlanRepository planRepository, ClientRepository clientRepository) {
        this.filePath = filePath;
        this.planRepository = planRepository;
        this.clientRepository = clientRepository;
        this.subscriptions = deserializeSubscriptions();
    }

    public List<Subscription> deserializeSubscriptions() {
        File file = new File(filePath);
        if(!file.exists()){
            return new ArrayList<>();
        }
        try{
            ObjectMapper mapper = new ObjectMapper();
            List<SubscriptionData> subscriptionData =
                    mapper.readValue(file, new TypeReference<List<SubscriptionData>>() {});
            List<Subscription> realSubscriptions = new ArrayList<>();
            for(SubscriptionData data : subscriptionData){

                Client client = this.clientRepository.findByDni
                        (data.clientDni()).orElseThrow(() -> new IllegalArgumentException("Client not found"));

                SubscriptionPlan plan = this.planRepository.findById
                        (data.planId()).orElseThrow(() -> new IllegalArgumentException("Plan not found"));

                Subscription sub = new Subscription(,
                        client,
                        plan,
                        data.startDate(),
                        data.state(),
                        data.nextBillingDate());
                realSubscriptions.add(sub);
            }
            return realSubscriptions;
        }catch (IOException e){
            System.err.println("Error deserializing Subscription plans.");
            return new ArrayList<>();
        }
    }

    private void serializeSubscriptions(){
        List<SubscriptionData> subscriptionData = new ArrayList<>();
        for(Subscription sub : this.subscriptions){
            subscriptionData.add(new SubscriptionData(sub));
        }
        try{
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(filePath);
            mapper.writeValue(file, subscriptionData);
        }catch(IOException e){
            throw new RuntimeException("Error serializing Subscription plans.");
        }
    }

    @Override
    public Optional<Subscription> findActiveSubscription(String clientDni, int planId){
        return subscriptions.stream()
                .filter(sub -> sub.getClient().getDni().equals(clientDni))
                .filter(sub -> sub.getPlan().id() == planId)
                .filter(sub -> sub.getState() == SubscriptionState.ACTIVE)
                .findFirst();
    }

    @Override
    public void save(Subscription subscription) {
        subscriptions.add(subscription);
        serializeSubscriptions();
    }

    @Override
    public Optional<Subscription> findByClientDni(String dni) {
        for(Subscription sub : subscriptions){
            if(sub.getClient().getDni().equals(dni)){
                return Optional.of(sub);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Subscription> findAll() {
        return this.subscriptions;
    }
}
