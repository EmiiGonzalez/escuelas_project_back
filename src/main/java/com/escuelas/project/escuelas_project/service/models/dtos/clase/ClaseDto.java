package com.escuelas.project.escuelas_project.service.models.dtos.clase;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClaseDto(
        @NotBlank(message = "La fecha de la clase es requerida para la creacion de una nueva clase") @Pattern(message = "La fecha debe tener el formato dd-MM-yyyy", regexp = "^\\d{2}-\\d{2}-\\d{4}$") String fecha,
        @NotBlank(message = "El contenido de la clase es requerido para la creacion de una nueva clase")
        String contenido,
        @NotBlank(message =  "Se debe indicar el numero de clase")
        @Pattern(message = "El numero de clase debe ser un entero", regexp = "^\\d+$")
        Integer numeroDeClase) {

}
