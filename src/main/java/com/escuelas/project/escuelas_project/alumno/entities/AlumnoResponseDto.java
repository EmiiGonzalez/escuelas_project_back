package com.escuelas.project.escuelas_project.alumno.entities;

public record AlumnoResponseDto(
    Long id,
    String nombre,
    String telefono

) {
    public AlumnoResponseDto(Alumno alumno) {
        this(alumno.getId_alumno(), alumno.getNombre(), alumno.getTelefono());
    }

}
