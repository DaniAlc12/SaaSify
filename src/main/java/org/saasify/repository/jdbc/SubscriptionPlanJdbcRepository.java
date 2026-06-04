package org.saasify.repository.jdbc;

import org.saasify.config.DatabaseConnection;
import org.saasify.models.SubscriptionPlan;
import org.saasify.repository.SubscriptionPlanRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubscriptionPlanJdbcRepository implements SubscriptionPlanRepository {

    @Override
    public Optional<SubscriptionPlan> findById(int id) {
        String sql = "SELECT * FROM subscription_plans WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new SubscriptionPlan(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getBigDecimal("price"),
                            rs.getInt("period_months")
                    ));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<SubscriptionPlan> listAll() {
        String sql = "SELECT * FROM subscription_plans";
        List<SubscriptionPlan> subscriptionPlans = new ArrayList<>();
        try(Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);){
            try(ResultSet  rs = ps.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    BigDecimal price = rs.getBigDecimal("price");
                    int period_months = rs.getInt("period_months");

                    SubscriptionPlan subscriptionPlan = new SubscriptionPlan(id, name, price, period_months);
                    subscriptionPlans.add(subscriptionPlan);
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return subscriptionPlans;
    }
}
