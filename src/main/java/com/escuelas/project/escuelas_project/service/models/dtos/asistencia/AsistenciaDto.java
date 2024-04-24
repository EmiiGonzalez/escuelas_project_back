package com.escuelas.project.escuelas_project.service.models.dtos.asistencia;

import jakarta.validation.constraints.NotBlank;

public record AsistenciaDto(
    @NotBlank (message = "La asistencia es requerida para la creacion de una nueva asistencia")
    Boolean asistio
) {
} 