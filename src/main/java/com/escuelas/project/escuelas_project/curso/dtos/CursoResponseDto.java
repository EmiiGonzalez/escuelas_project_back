package com.escuelas.project.escuelas_project.curso.dtos;

import java.util.UUID;

import com.escuelas.project.escuelas_project.curso.entities.Curso;

public record CursoResponseDto(
    UUID id,
    String nombre,
    String escuela,
    String materia,
    String escuelaId
) {
    public CursoResponseDto(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getEscuela().getNombre(), curso.getMateria(), curso.getEscuela().getId().toString());
    }
}
