package com.sofka.ms_users.service;

import com.sofka.ms_users.dto.PersonaDTO;
import com.sofka.ms_users.model.Persona;
import com.sofka.ms_users.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    public List<Persona> findAll() {
        return personaRepository.findAll();
    }

    public Optional<Persona> findById(String identificacion) {
        return personaRepository.findByIdentificacion(identificacion);
    }

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

    public void delete(String identificacion) {
        personaRepository.deleteById(identificacion);
    }
}