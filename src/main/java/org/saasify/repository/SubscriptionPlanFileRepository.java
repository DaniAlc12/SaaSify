package org.saasify.repository;

import org.saasify.models.SubscriptionPlan;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubscriptionPlanFileRepository implements SubscriptionPlanRepository {

    private final String filePath;
    private List<SubscriptionPlan> subscriptionPlans;

    public SubscriptionPlanFileRepository(String filePath) {
        if(filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("filePath is null or blank");
        }
        this.filePath = filePath;
        this.subscriptionPlans = deserializePlans();
    }

    private void serializePlans(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))){
            oos.writeObject(subscriptionPlans);
        }catch(IOException e){
            throw new RuntimeException("Error serializing subscription plans.");
        }
    }

    public List<SubscriptionPlan> deserializePlans() {
        File file = new File(filePath);
        if(!file.exists()){
            List<SubscriptionPlan> defaultPlans = new ArrayList<>();
            defaultPlans.add(new SubscriptionPlan(1,"Basic", new BigDecimal(9.99), 1));
            defaultPlans.add(new SubscriptionPlan(2,"Pro", new BigDecimal(29.99), 6));
            defaultPlans.add(new SubscriptionPlan(3,"Premium", new BigDecimal(49.99), 12));
            this.subscriptionPlans = defaultPlans;
            serializePlans();
            return defaultPlans;
        }

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))){
            return (List<SubscriptionPlan>) ois.readObject();
        }catch (IOException | ClassNotFoundException e){
            System.err.println("Error deserializing Subscription plans.");
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<SubscriptionPlan> findById(int id) {
        for(SubscriptionPlan sp : subscriptionPlans){
            if(sp.getId() == id){
               return Optional.of(sp);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<SubscriptionPlan> listAll() {
        return subscriptionPlans;
    }
}
