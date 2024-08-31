package com.escuelas.project.escuelas_project.asistencia.entities;

import io.micrometer.common.lang.Nullable;

public record AsistenciaUpdateDto(
        @Nullable Boolean asistio) {
}
