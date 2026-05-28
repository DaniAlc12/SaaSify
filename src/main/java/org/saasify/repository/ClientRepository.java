package org.saasify.repository;

import org.saasify.models.Client;
import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Optional<Client> findByDni(String dni);
    List<Client> findAll();
    void save(Client client);
}
