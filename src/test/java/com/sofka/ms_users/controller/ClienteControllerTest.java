package com.sofka.ms_users.controller;

import com.sofka.ms_users.model.Cliente;
import com.sofka.ms_users.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClienteControllerTest {

    private MockMvc mockMvc;
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        clienteService = Mockito.mock(ClienteService.class);
        ClienteController controller = new ClienteController(clienteService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldGetClienteById() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setClienteId(1L);
        cliente.setNombre("Test User");

        when(clienteService.findById(1L)).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test User"));
    }
}
