package org.saasify.repository.jdbc;

import org.saasify.config.DatabaseConnection;
import org.saasify.models.Client;
import org.saasify.repository.ClientRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientJdbcRepository implements ClientRepository {
    @Override
    public Optional<Client> findByDni(String dni) {
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        return new ArrayList<>();
    }

    @Override
    public void save(Client client) {
        String sql = "INSERT INTO CLIENTS (id , dni, name , email , " +
                "password , bank_balance) VALUES (?, ?, ?, ?, ?, ?)";
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
                ps.setObject(1, client.getId());
                ps.setString(2, client.getDni());
                ps.setString(3, client.getName());
                ps.setString(4, client.getEmail());
                ps.setString(5, client.getPassword());
                ps.setBigDecimal(6, client.getBankBalance());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
