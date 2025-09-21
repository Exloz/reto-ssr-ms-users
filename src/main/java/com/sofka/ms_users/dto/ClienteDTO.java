package com.sofka.ms_users.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClienteDTO extends PersonaDTO {

    @NotBlank(message = "Contraseña is required")
    @Size(min = 4, max = 20, message = "Contraseña must be between 4 and 20 characters")
    private String contrasena;

    @NotNull(message = "Estado is required")
    private Boolean estado;
}