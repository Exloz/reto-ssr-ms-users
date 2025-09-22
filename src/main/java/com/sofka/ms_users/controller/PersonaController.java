package com.sofka.ms_users.controller;

import com.sofka.ms_users.dto.PersonaDTO;
import com.sofka.ms_users.model.Persona;
import com.sofka.ms_users.service.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/personas")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaService personaService;

    @GetMapping
    @Operation(summary = "Get all personas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    public ResponseEntity<List<Persona>> getAllPersonas() {
        return ResponseEntity.ok(personaService.findAll());
    }

    @GetMapping("/{identificacion}")
    @Operation(summary = "Get persona by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Persona not found")
    })
    public ResponseEntity<Persona> getPersonaById(@PathVariable String identificacion) {
        return personaService.findById(identificacion)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new persona")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Persona created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<Persona> createPersona(@Valid @RequestBody PersonaDTO personaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personaService.save(personaDTO));
    }

    @PutMapping("/{identificacion}")
    @Operation(summary = "Update persona")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Persona not found")
    })
    public ResponseEntity<Persona> updatePersona(@PathVariable String identificacion, @Valid @RequestBody PersonaDTO personaDTO) {
        try {
            return ResponseEntity.ok(personaService.update(identificacion, personaDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{identificacion}")
    @Operation(summary = "Delete persona")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Persona deleted"),
            @ApiResponse(responseCode = "404", description = "Persona not found")
    })
    public ResponseEntity<Void> deletePersona(@PathVariable String identificacion) {
        personaService.delete(identificacion);
        return ResponseEntity.noContent().build();
    }
}