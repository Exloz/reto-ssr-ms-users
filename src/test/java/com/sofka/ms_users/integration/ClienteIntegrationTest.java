package com.sofka.ms_users.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(ClienteIntegrationTest.StreamBridgeTestConfig.class)
class ClienteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StreamBridge streamBridge;

    @BeforeEach
    void setUp() {
        when(streamBridge.send(eq("clienteCreated-out-0"), any())).thenReturn(true);
    }

    @Test
    void shouldCreateAndRetrieveCliente() throws Exception {
        JsonNode requestPayload = objectMapper.createObjectNode()
                .put("identificacion", "1234567890")
                .put("nombre", "Test User")
                .put("genero", "M")
                .put("edad", 30)
                .put("direccion", "Street 123")
                .put("telefono", "0987654321")
                .put("contrasena", "pass1234")
                .put("estado", true);

        MvcResult createResult = mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestPayload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Test User"))
                .andReturn();

        JsonNode responseJson = objectMapper.readTree(createResult.getResponse().getContentAsString());
        Long clienteId = responseJson.get("clienteId").asLong();
        assertThat(clienteId).isPositive();

        mockMvc.perform(get("/clientes/" + clienteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteId").value(clienteId))
                .andExpect(jsonPath("$.identificacion").value("1234567890"))
                .andExpect(jsonPath("$.nombre").value("Test User"));

        verify(streamBridge).send(eq("clienteCreated-out-0"), any());
}

    @TestConfiguration
    static class StreamBridgeTestConfig {
        @Bean
        StreamBridge streamBridge() {
            return org.mockito.Mockito.mock(StreamBridge.class);
        }
    }
}
