package com.escuelas.project.escuelas_project.clase.dtos;

import io.micrometer.common.lang.Nullable;

public record ClaseUpdateDto(
    @Nullable
    String fecha,
    @Nullable
    String contenido,
    @Nullable
    Integer numeroDeClase
) {
} 
