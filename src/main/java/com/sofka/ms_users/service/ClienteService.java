package com.sofka.ms_users.service;

import com.sofka.ms_users.dto.ClienteDTO;
import com.sofka.ms_users.event.ClienteCreatedEvent;
import com.sofka.ms_users.model.Cliente;
import com.sofka.ms_users.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService implements ClienteServiceInterface {

    private final ClienteRepository clienteRepository;
    private final EventProducerService eventProducerService;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Long clienteId) {
        return clienteRepository.findByClienteId(clienteId);
    }

    public List<Cliente> findByEstado(Boolean estado) {
        return clienteRepository.findByEstado(estado);
    }

    @Transactional
    public Cliente save(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setIdentificacion(clienteDTO.getIdentificacion());
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setGenero(clienteDTO.getGenero());
        cliente.setEdad(clienteDTO.getEdad());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setContrasena(clienteDTO.getContrasena());
        cliente.setEstado(clienteDTO.getEstado());
        cliente.setClienteId(generateClienteId());
        Cliente savedCliente = clienteRepository.save(cliente);
        eventProducerService.sendClienteCreatedEvent(new ClienteCreatedEvent(savedCliente.getClienteId(), savedCliente.getNombre(), savedCliente.getIdentificacion()));
        return savedCliente;
    }

    @Transactional
    public Cliente update(Long clienteId, ClienteDTO clienteDTO) {
        Optional<Cliente> existing = clienteRepository.findByClienteId(clienteId);
        if (existing.isPresent()) {
            Cliente cliente = existing.get();
            cliente.setIdentificacion(clienteDTO.getIdentificacion());
            cliente.setNombre(clienteDTO.getNombre());
            cliente.setGenero(clienteDTO.getGenero());
            cliente.setEdad(clienteDTO.getEdad());
            cliente.setDireccion(clienteDTO.getDireccion());
            cliente.setTelefono(clienteDTO.getTelefono());
            cliente.setContrasena(clienteDTO.getContrasena());
            cliente.setEstado(clienteDTO.getEstado());
            return clienteRepository.save(cliente);
        }
        throw new RuntimeException("Cliente not found");
    }

    @Transactional
    public void delete(Long clienteId) {
        Cliente existing = clienteRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente not found"));
        clienteRepository.delete(existing);
    }

    private Long generateClienteId() {
        return clienteRepository.getNextClienteId();
    }
}
