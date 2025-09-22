package com.sofka.ms_users.service;

import com.sofka.ms_users.dto.PersonaDTO;
import com.sofka.ms_users.model.Persona;
import com.sofka.ms_users.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaService implements PersonaServiceInterface {

    private final PersonaRepository personaRepository;

    public List<Persona> findAll() {
        return personaRepository.findAll();
    }

    public Optional<Persona> findById(String identificacion) {
        return personaRepository.findByIdentificacion(identificacion);
    }

    @Transactional
    public Persona save(PersonaDTO personaDTO) {
        Persona persona = new Persona();
        persona.setIdentificacion(personaDTO.getIdentificacion());
        persona.setNombre(personaDTO.getNombre());
        persona.setGenero(personaDTO.getGenero());
        persona.setEdad(personaDTO.getEdad());
        persona.setDireccion(personaDTO.getDireccion());
        persona.setTelefono(personaDTO.getTelefono());
        return personaRepository.save(persona);
    }

    @Transactional
    public Persona update(String identificacion, PersonaDTO personaDTO) {
        Optional<Persona> existing = personaRepository.findByIdentificacion(identificacion);
        if (existing.isPresent()) {
            Persona persona = existing.get();
            persona.setNombre(personaDTO.getNombre());
            persona.setGenero(personaDTO.getGenero());
            persona.setEdad(personaDTO.getEdad());
            persona.setDireccion(personaDTO.getDireccion());
            persona.setTelefono(personaDTO.getTelefono());
            return personaRepository.save(persona);
        }
        throw new RuntimeException("Persona not found");
    }

    @Transactional
    public void delete(String identificacion) {
        personaRepository.deleteById(identificacion);
    }
}