package org.saasify.repository.jdbc;

import org.saasify.config.DatabaseConnection;
import org.saasify.models.Subscription;
import org.saasify.repository.SubscriptionRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class SubscriptionJdbcRepository implements SubscriptionRepository {
    @Override
    public void save(Subscription subscription) {
        String sql = "INSERT INTO subscriptions (id, client_id, plan_id, start_date, state) VALUES (?, ?, ?, ?, ?)";
        try(Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setObject(1, subscription.getId());
            ps.setObject(2, subscription.getClient().getId());
            ps.setInt(3, subscription.getPlan().id());
            ps.setDate(4, Date.valueOf(subscription.getStartDate()));
            ps.setString(5, subscription.getState().name());

            ps.executeUpdate();

        }catch(SQLException e){
            throw new RuntimeException("Error saving subscription",e);
        }
    }

    @Override
    public Optional<Subscription> findByClientDni(String dni) {

        return Optional.empty();
    }

    @Override
    public List<Subscription> findAll() {
        return List.of();
    }

    @Override
    public Optional<Subscription> findActiveSubscription(String clientDni, int planId) {
        String sql = "SELECT s.* FROM subscription s " + "JOIN clients c ON s.client_id = c.id "
                + "WHERE c.dni = ? AND s.plan_id = ? AND s.state = 'ACTIVE' LIMIT 1";

        try(Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, clientDni);
            ps.setInt(2, planId);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return Optional.of(new Subscription());
                }
            }
        }catch(SQLException e){
            throw new RuntimeException("Error getting subscription",e);
        }

        return Optional.empty();
    }
}
