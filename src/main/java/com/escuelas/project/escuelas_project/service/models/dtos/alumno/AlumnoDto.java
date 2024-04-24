package com.escuelas.project.escuelas_project.service.models.dtos.alumno;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;

public record AlumnoDto(
    @NotBlank(message = "{alumno.nombre.not.blank}")
    String nombre,
    @Nullable
    String telefono
) {
    
}
