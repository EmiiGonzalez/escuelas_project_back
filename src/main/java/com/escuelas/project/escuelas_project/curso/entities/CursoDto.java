package com.escuelas.project.escuelas_project.curso.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CursoDto(
    @NotBlank(message = "{curso.nombre.not.blank}")
    String nombre,
    @NotBlank(message = "{curso.fecha.not.blank}")
    @Pattern(message = "{curso.pattern.message}", regexp = "^\\d{2}-\\d{2}-\\d{4}$")
    String fecha,

    @NotBlank(message = "{curso.materia.not.blank}")
    String materia
) {
} 