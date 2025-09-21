package com.sofka.ms_users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofka.ms_users.dto.ClienteDTO;
import com.sofka.ms_users.model.Cliente;
import com.sofka.ms_users.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    public void shouldGetClienteById() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setClienteId(1L);
        cliente.setNombre("Test User");

        when(clienteService.findById(1L)).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test User"));
    }
}