package com.sofka.ms_users.repository;

import com.sofka.ms_users.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByClienteId(Long clienteId);
    List<Cliente> findByEstado(Boolean estado);
}