package com.sofka.ms_users.service;

import com.sofka.ms_users.dto.ClienteDTO;
import com.sofka.ms_users.model.Cliente;
import com.sofka.ms_users.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        clienteDTO.setGenero("M");
        clienteDTO.setEdad(30);
        clienteDTO.setDireccion("Street 123");
        clienteDTO.setTelefono("0987654321");
        clienteDTO.setContrasena("password");
        clienteDTO.setEstado(true);

        Cliente cliente = new Cliente();
        cliente.setClienteId(1L);
        cliente.setIdentificacion("1234567890");
        cliente.setNombre("Test User");
        cliente.setGenero("M");
        cliente.setEdad(30);
        cliente.setDireccion("Street 123");
        cliente.setTelefono("0987654321");
        cliente.setContrasena("password");
        cliente.setEstado(true);

        when(clienteRepository.getNextClienteId()).thenReturn(1L);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente result = clienteService.save(clienteDTO);

        assertNotNull(result);
        assertEquals("Test User", result.getNombre());
        verify(eventProducerService, times(1)).sendClienteCreatedEvent(any());
    }

    @Test
    void shouldPropagateErrorWhenEventPublishingFails() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setIdentificacion("1234567890");
        clienteDTO.setNombre("Test User");
        clienteDTO.setGenero("M");
        clienteDTO.setEdad(30);
        clienteDTO.setDireccion("Street 123");
        clienteDTO.setTelefono("0987654321");
        clienteDTO.setContrasena("password");
        clienteDTO.setEstado(true);

        Cliente cliente = new Cliente();
        cliente.setClienteId(1L);
        cliente.setIdentificacion("1234567890");

        when(clienteRepository.getNextClienteId()).thenReturn(1L);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        doThrow(new IllegalStateException("Unable to publish ClienteCreatedEvent")).when(eventProducerService).sendClienteCreatedEvent(any());

        assertThrows(IllegalStateException.class, () -> clienteService.save(clienteDTO));
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }
}
