package com.escuelas.project.escuelas_project.escuela.dtos;

import jakarta.validation.constraints.NotBlank;
public record EscuelaDto(
    @NotBlank(message = "{escuela.nombre.not.blank}")
    String nombre
) {
}
