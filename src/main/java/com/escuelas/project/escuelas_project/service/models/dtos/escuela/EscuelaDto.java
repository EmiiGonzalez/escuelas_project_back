package com.escuelas.project.escuelas_project.service.models.dtos.escuela;

import jakarta.validation.constraints.NotBlank;
public record EscuelaDto(
    @NotBlank(message = "{escuela.nombre.not.blank}")
    String nombre
) {
}
