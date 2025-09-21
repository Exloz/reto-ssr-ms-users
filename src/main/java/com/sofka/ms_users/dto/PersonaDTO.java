package com.sofka.ms_users.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonaDTO {

    @NotBlank(message = "Identificación is required")
    private String identificacion;

    @NotBlank(message = "Nombre is required")
    @Size(max = 100, message = "Nombre must not exceed 100 characters")
    private String nombre;

    @NotBlank(message = "Genero is required")
    @Size(max = 10, message = "Genero must not exceed 10 characters")
    private String genero;

    @Min(value = 0, message = "Edad must be positive")
    @Max(value = 150, message = "Edad must be less than 150")
    private Integer edad;

    @Size(max = 200, message = "Dirección must not exceed 200 characters")
    private String direccion;

    @Pattern(regexp = "^\\d{10}$", message = "Teléfono must be 10 digits")
    private String telefono;
}