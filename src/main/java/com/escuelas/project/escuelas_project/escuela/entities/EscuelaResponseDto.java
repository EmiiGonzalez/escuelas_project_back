package com.escuelas.project.escuelas_project.escuela.entities;

public record EscuelaResponseDto( Long id, String nombre) {

    public EscuelaResponseDto(Escuela escuela) {
        this(escuela.getId_escuela(), escuela.getNombre());
    }
}
