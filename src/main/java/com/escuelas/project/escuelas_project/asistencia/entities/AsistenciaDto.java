package com.escuelas.project.escuelas_project.asistencia.entities;

import jakarta.validation.constraints.NotNull;

public record AsistenciaDto(
    @NotNull(message = "{asistencia.id.not.blank}")
    Long id,
    @NotNull(message = "{asistencia.asistio.not.blank}")
    Boolean asistio
) {
}