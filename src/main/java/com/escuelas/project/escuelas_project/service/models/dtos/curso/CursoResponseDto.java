package com.escuelas.project.escuelas_project.service.models.dtos.curso;

import com.escuelas.project.escuelas_project.persistence.entity.Curso;

public record CursoResponseDto(
    Long id,
    String nombre,
    String escuela
) {
    public CursoResponseDto(Curso curso) {
        this(curso.getId_curso(), curso.getNombre(), curso.getEscuela().getNombre());
    }
}
