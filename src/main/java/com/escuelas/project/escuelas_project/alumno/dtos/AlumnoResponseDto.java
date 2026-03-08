package com.escuelas.project.escuelas_project.alumno.dtos;

import java.util.UUID;

import com.escuelas.project.escuelas_project.alumno.entities.Alumno;

public record AlumnoResponseDto(
    UUID id,
    String nombre,
    String telefono

) {
    public AlumnoResponseDto(Alumno alumno) {
        this(alumno.getId(), alumno.getNombre(), alumno.getTelefono());
    }

}
