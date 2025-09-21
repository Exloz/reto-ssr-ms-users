package com.sofka.ms_users.controller;

import com.sofka.ms_users.dto.ClienteDTO;
import com.sofka.ms_users.model.Cliente;
import com.sofka.ms_users.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Get all clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    public ResponseEntity<List<Cliente>> getAllClientes() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/{clienteId}")
    @Operation(summary = "Get cliente by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Cliente not found")
    })
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long clienteId) {
        return clienteService.findById(clienteId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Cliente>> getClientesByEstado(@PathVariable Boolean estado) {
        return ResponseEntity.ok(clienteService.findByEstado(estado));
    }

    @PostMapping
    @Operation(summary = "Create a new cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<Cliente> createCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(clienteDTO));
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long clienteId, @Valid @RequestBody ClienteDTO clienteDTO) {
        try {
            return ResponseEntity.ok(clienteService.update(clienteId, clienteDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long clienteId) {
        clienteService.delete(clienteId);
        return ResponseEntity.noContent().build();
    }
}