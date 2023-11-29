package com.argentinaprograma.app.tpji2023.repository;

import java.util.List;
import com.argentinaprograma.app.tpji2023.modelo.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class ClienteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public ClienteRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Cliente> findAll() {
        return entityManager.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    }

    public Cliente findByCuit(String cuit) {
        return entityManager.createQuery("SELECT c FROM Cliente c WHERE c.cuit = :cuit", Cliente.class)
                            .setParameter("cuit", cuit)
                            .getSingleResult();
    }

    public Cliente findByRazonSocialIgnoreCase(String razonSocial) {
        return entityManager.createQuery("SELECT c FROM Cliente c WHERE LOWER(c.razonSocial) = LOWER(:razonSocial)", Cliente.class)
                            .setParameter("razonSocial", razonSocial)
                            .getSingleResult();
    }

    public Cliente findById(int idCliente) {
        return entityManager.find(Cliente.class, idCliente);
    }
}