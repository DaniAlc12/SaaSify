package org.saasify.repository.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.saasify.models.Client;
import org.saasify.repository.ClientRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientFileRepository implements ClientRepository {

    private final String filePath;
    private final List<Client> clients;

    public ClientFileRepository(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("filePath is null or blank");
        }
        this.filePath = filePath;
        this.clients = deserializeClients();
    }

    @SuppressWarnings("unchecked")
    private List<Client> deserializeClients() {
        File file = new File(filePath);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(file, new TypeReference<List<Client>>() {
            });
        } catch (IOException e) {
            System.err.println("Error deserializando clientes.");
            return new ArrayList<>();
        }
    }

    private void serializeClients() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(this.filePath), clients);
        } catch (IOException e) {
            throw new RuntimeException("Error serializando clientes.");
        }
    }

    @Override
    public Optional<Client> findByDni(String dni) {
        for (Client client : clients) {
            if (client.getDni().equals(dni)) {
                return Optional.of(client);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        return clients;
    }

    @Override
    public void save(Client client) {
        clients.add(client);
        serializeClients();
    }
}

