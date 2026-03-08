package com.escuelas.project.escuelas_project.clase.dtos;

import java.util.UUID;

import com.escuelas.project.escuelas_project.clase.entities.Clase;

public record ClaseResponseDto(
    UUID id,
    String fecha,
    String contenido,
    Integer numeroDeClase,
    Boolean asistencia,
    UUID idCurso
) {
    public ClaseResponseDto(Clase clase) {
        this(clase.getId(), clase.getFechaString(), clase.getContenido(), clase.getNumero_de_clase(), clase.getAsistencias().size() > 0, clase.getCurso().getId());
    }
}
