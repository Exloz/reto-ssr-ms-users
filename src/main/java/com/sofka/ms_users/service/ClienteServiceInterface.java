package com.sofka.ms_users.service;

import com.sofka.ms_users.dto.ClienteDTO;
import com.sofka.ms_users.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteServiceInterface {
    List<Cliente> findAll();
    Optional<Cliente> findById(Long clienteId);
    List<Cliente> findByEstado(Boolean estado);
    Cliente save(ClienteDTO clienteDTO);
    Cliente update(Long clienteId, ClienteDTO clienteDTO);
    void delete(Long clienteId);
}