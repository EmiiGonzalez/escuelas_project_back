package com.escuelas.project.escuelas_project.service.models.dtos.curso;

import jakarta.validation.constraints.NotBlank;

public record CursoDto(
    @NotBlank(message = "El nombre del curso es requerido para la creacion de un nuevo curso")
    String nombre,
    @NotBlank(message = "La fecha del curso es requerida para la creacion de un nuevo curso")
    String fecha
) {
} 