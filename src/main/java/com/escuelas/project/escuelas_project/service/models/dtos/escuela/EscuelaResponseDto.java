package com.escuelas.project.escuelas_project.service.models.dtos.escuela;

import com.escuelas.project.escuelas_project.persistence.entity.Escuela;

public record EscuelaResponseDto( Long id, String nombre) {

    public EscuelaResponseDto(Escuela escuela) {
        this(escuela.getId_escuela(), escuela.getNombre());
    }
}
