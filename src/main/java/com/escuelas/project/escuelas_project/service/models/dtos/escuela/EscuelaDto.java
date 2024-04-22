package com.escuelas.project.escuelas_project.service.models.dtos.escuela;

import jakarta.validation.constraints.NotBlank;
public record EscuelaDto(
    @NotBlank(message = "El nombre de la escuela es requerido para la creacion de una nueva escuela")
    String nombre
) {
}
