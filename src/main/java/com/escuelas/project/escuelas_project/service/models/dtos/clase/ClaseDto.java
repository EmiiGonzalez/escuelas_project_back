package com.escuelas.project.escuelas_project.service.models.dtos.clase;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClaseDto(
        @NotBlank(message = "{clase.fecha.not.blank}") @Pattern(message = "{clase.fecha.pattern.message}", regexp = "^\\d{2}-\\d{2}-\\d{4}$") String fecha,
        @NotBlank(message = "El contenido de la clase es requerido para la creacion de una nueva clase")
        String contenido,
        @NotBlank(message =  "{clase.contenido.not.blank}")
        @Pattern(message = "{clase.contenido.pattern.message}", regexp = "^\\d+$")
        Integer numeroDeClase) {

}
