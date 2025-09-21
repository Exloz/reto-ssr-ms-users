package com.sofka.ms_users.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
public class Persona {

    @Id
    @Column(name = "identificacion")
    @NotBlank(message = "Identificación is required")
    private String identificacion;

    @Column(name = "nombre")
    @NotBlank(message = "Nombre is required")
    @Size(max = 100, message = "Nombre must not exceed 100 characters")
    private String nombre;

    @Column(name = "genero")
    @NotBlank(message = "Genero is required")
    @Size(max = 10, message = "Genero must not exceed 10 characters")
    private String genero;

    @Column(name = "edad")
    @Min(value = 0, message = "Edad must be positive")
    @Max(value = 150, message = "Edad must be less than 150")
    private Integer edad;

    @Column(name = "direccion")
    @Size(max = 200, message = "Dirección must not exceed 200 characters")
    private String direccion;

    @Column(name = "telefono")
    @Pattern(regexp = "^\\d{10}$", message = "Teléfono must be 10 digits")
    private String telefono;
}