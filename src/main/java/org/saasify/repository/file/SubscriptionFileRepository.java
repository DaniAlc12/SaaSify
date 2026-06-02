package org.saasify.repository.file;

import org.saasify.models.Client;
import org.saasify.models.Subscription;
import org.saasify.models.SubscriptionData;
import org.saasify.models.SubscriptionPlan;
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
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))){
            List<SubscriptionData> subscriptionData = (List<SubscriptionData>) ois.readObject();
            List<Subscription> realSubscriptions = new ArrayList<>();
            for(SubscriptionData data : subscriptionData){

                Client client = this.clientRepository.findByDni
                        (data.clientDni()).orElseThrow(() -> new IllegalArgumentException("Client not found"));

                SubscriptionPlan plan = this.planRepository.findById
                        (data.planId()).orElseThrow(() -> new IllegalArgumentException("Plan not found"));

                Subscription sub = new Subscription(
                        client,
                        plan,
                        data.startDate(),
                        data.state(),
                        data.nextBillingDate()
                );
                realSubscriptions.add(sub);
            }
            return realSubscriptions;
        }catch (IOException | ClassNotFoundException e){
            System.err.println("Error deserializing Subscription plans.");
            return new ArrayList<>();
        }
    }

    private void serializeSubscriptions(){
        List<SubscriptionData> subscriptionData = new ArrayList<>();
        for(Subscription sub : this.subscriptions){
            subscriptionData.add(new SubscriptionData(sub));
        }
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))){
            oos.writeObject(subscriptionData);
        }catch(IOException e){
            throw new RuntimeException("Error serializando clientes.");
        }
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
