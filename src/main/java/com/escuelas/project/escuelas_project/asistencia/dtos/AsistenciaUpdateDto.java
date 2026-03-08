package com.escuelas.project.escuelas_project.asistencia.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AsistenciaUpdateDto(
        @NotBlank(message = "{asistencia.asistio.not.blank}") @Pattern(regexp = "PRESENTE|AUSENTE|JUSTIFICADO|NOENLISTADO", message = "{asistencia.asistio.invalid}") String asistio) {
}
