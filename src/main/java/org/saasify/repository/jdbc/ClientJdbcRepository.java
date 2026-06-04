package org.saasify.repository.jdbc;

import org.saasify.config.DatabaseConnection;
import org.saasify.models.Client;
import org.saasify.repository.ClientRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientJdbcRepository implements ClientRepository {
    @Override
    public Optional<Client> findByDni(String dni) {
        String sql = "SELECT id, dni, name ,email, password, bank_balance" +
                "FROM clients WHERE dni = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, dni);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    UUID id = (UUID) resultSet.getObject("id");
                    String clientDni = resultSet.getString("dni");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    BigDecimal bankBalance = resultSet.getBigDecimal("balance");

                    Client client = new Client(id, clientDni, name, email, password, bankBalance);
                    return Optional.of(client);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    UUID id = (UUID) resultSet.getObject("id");
                    String clientDni = resultSet.getString("dni");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    BigDecimal bankBalance = resultSet.getBigDecimal("balance");

                    Client client = new Client(id, clientDni, name, email, password, bankBalance);
                    clients.add(client);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return clients;
    }

    @Override
    public void save(Client client) {
        String sql = "INSERT INTO CLIENTS (id , dni, name , email , " +
                "password , bank_balance) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
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
