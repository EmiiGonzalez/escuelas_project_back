package com.escuelas.project.escuelas_project.asistencia.dtos;

import java.util.UUID;

import com.escuelas.project.escuelas_project.asistencia.entities.Asistencia;

public record AsistenciaResponsePorClaseDto(
    UUID id,
    String asistio,
    String alumno
) {
    public AsistenciaResponsePorClaseDto(Asistencia asistencia) {
        this(asistencia.getId(), asistencia.getAsistio().toString(), asistencia.getAlumno().getNombre());
    }
}
