package com.escuelas.project.escuelas_project.service.models.dtos.alumno;

import com.escuelas.project.escuelas_project.persistence.entity.Alumno;

public record AlumnoResponseDto(
    Long id,
    String nombre,
    String telefono

) {
    public AlumnoResponseDto(Alumno alumno) {
        this(alumno.getId_alumno(), alumno.getNombre(), alumno.getTelefono());
    }

}
