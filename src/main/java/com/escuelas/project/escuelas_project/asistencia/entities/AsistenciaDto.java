package com.escuelas.project.escuelas_project.asistencia.entities;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AsistenciaDto(
    @NotNull(message = "{asistencia.id.not.blank}")
    Long id,
    
    @NotNull(message = "{asistencia.asistio.not.blank}")
    @Pattern(regexp = "PRESENTE|AUSENTE|JUSTIFICADO|NOENLISTADO", message = "{asistencia.asistio.invalid}")
    String asistio
) {
}