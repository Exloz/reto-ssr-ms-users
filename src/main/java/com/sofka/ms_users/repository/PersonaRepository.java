package com.sofka.ms_users.repository;

import com.sofka.ms_users.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, String> {
    Optional<Persona> findByIdentificacion(String identificacion);
}