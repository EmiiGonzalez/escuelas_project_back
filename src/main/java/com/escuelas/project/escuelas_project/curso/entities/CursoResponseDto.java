package com.escuelas.project.escuelas_project.curso.entities;

public record CursoResponseDto(
    Long id,
    String nombre,
    String escuela,
    String materia
) {
    public CursoResponseDto(Curso curso) {
        this(curso.getId_curso(), curso.getNombre(), curso.getEscuela().getNombre(), curso.getMateria());
    }
}
