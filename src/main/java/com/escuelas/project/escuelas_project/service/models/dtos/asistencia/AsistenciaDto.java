package com.escuelas.project.escuelas_project.service.models.dtos.asistencia;

import jakarta.validation.constraints.NotBlank;

public record AsistenciaDto(
    @NotBlank (message = "{asistencia.asistio.not.blank}")
    Boolean asistio
) {
} 