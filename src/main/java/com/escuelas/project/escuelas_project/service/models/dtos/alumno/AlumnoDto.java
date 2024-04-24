package com.escuelas.project.escuelas_project.service.models.dtos.alumno;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;

public record AlumnoDto(
    @NotBlank(message = "El nombre del Alumno es requerido para la creacion de un nuevo Alumno")
    String nombre,
    @Nullable
    String telefono
) {
    
}
