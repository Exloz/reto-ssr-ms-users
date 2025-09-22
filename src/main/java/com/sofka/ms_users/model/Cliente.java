package com.sofka.ms_users.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "identificacion")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Cliente extends Persona {

    @Column(name = "cliente_id", unique = true)
    private Long clienteId;

    @Column(name = "contrasena")
    @NotBlank(message = "Contraseña is required")
    @Size(min = 4, max = 20, message = "Contraseña must be between 4 and 20 characters")
    private String contrasena;

    @Column(name = "estado")
    @NotNull(message = "Estado is required")
    private Boolean estado;
}
