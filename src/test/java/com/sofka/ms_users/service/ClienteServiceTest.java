package com.sofka.ms_users.service;

import com.sofka.ms_users.dto.ClienteDTO;
import com.sofka.ms_users.model.Cliente;
import com.sofka.ms_users.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EventProducerService eventProducerService;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    public void shouldSaveCliente() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setIdentificacion("1234567890");
        clienteDTO.setNombre("Test User");
        clienteDTO.setContrasena("password");
        clienteDTO.setEstado(true);

        Cliente cliente = new Cliente();
        cliente.setClienteId(1L);
        cliente.setIdentificacion("1234567890");
        cliente.setNombre("Test User");
        cliente.setContrasena("password");
        cliente.setEstado(true);

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente result = clienteService.save(clienteDTO);

        assertNotNull(result);
        assertEquals("Test User", result.getNombre());
        verify(eventProducerService, times(1)).sendClienteCreatedEvent(any());
    }
}