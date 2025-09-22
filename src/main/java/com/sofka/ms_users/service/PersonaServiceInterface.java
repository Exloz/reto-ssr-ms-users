package com.sofka.ms_users.service;

import com.sofka.ms_users.dto.PersonaDTO;
import com.sofka.ms_users.model.Persona;

import java.util.List;
import java.util.Optional;

public interface PersonaServiceInterface {
    List<Persona> findAll();
    Optional<Persona> findById(String identificacion);
    Persona save(PersonaDTO personaDTO);
    Persona update(String identificacion, PersonaDTO personaDTO);
    void delete(String identificacion);
}