package com.escuelas.project.escuelas_project.clase.entities;

public record ClaseResponseDto(
    Long id,
    String fecha,
    String contenido,
    Integer numeroDeClase
) {
    public ClaseResponseDto(Clase clase) {
        this(clase.getId_clase(), clase.getFechaString(), clase.getContenido(), clase.getNumero_de_clase());
    }
}
