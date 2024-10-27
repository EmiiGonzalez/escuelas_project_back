package com.escuelas.project.escuelas_project.asistencia.entities;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.Pattern;

public record AsistenciaUpdateDto(
                @Nullable @Pattern(regexp = "PRESENTE|AUSENTE|JUSTIFICADO|NOENLISTADO", message = "{asistencia.asistio.invalid}") String asistio) {
}
