package com.escuelas.project.escuelas_project.asistencia.dtos;

import java.util.UUID;

import com.escuelas.project.escuelas_project.asistencia.entities.Asistencia;

public record AsistenciaResponseDto(
        UUID id,
        Integer claseNumero,
        String fecha,
        AsistioEnum asistio) {
    public AsistenciaResponseDto(Asistencia asistencia) {
        this(asistencia.getId(), asistencia.getClase().getNumero_de_clase(), asistencia.getClase().getFechaString(), asistencia.getAsistio());
    }
}
