package com.escuelas.project.escuelas_project.escuela.dtos;

import java.util.UUID;

import com.escuelas.project.escuelas_project.escuela.entities.Escuela;

public record EscuelaResponseDto(UUID id, String nombre) {

    public EscuelaResponseDto(Escuela escuela) {
        this(escuela.getId(), escuela.getNombre());
    }
}
