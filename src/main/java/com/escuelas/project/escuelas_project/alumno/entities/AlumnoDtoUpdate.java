package com.escuelas.project.escuelas_project.alumno.entities;

import org.hibernate.validator.constraints.Length;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.Pattern;

public record AlumnoDtoUpdate(
    @Nullable
    @Pattern(message = "{alumno.telefono.pattern.message}", regexp = "^\\d{10}$")
    String telefono,
    @Nullable
    @Length(min = 3, max = 50, message = "{alumno.nombre.length.message}")
    String nombre
) {

}
