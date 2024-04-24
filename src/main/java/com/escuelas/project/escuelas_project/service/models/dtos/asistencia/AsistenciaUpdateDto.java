package com.escuelas.project.escuelas_project.service.models.dtos.asistencia;

import io.micrometer.common.lang.Nullable;

public record AsistenciaUpdateDto(
        @Nullable Boolean asistio) {
}
