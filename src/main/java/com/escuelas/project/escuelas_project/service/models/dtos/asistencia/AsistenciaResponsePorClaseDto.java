package com.escuelas.project.escuelas_project.service.models.dtos.asistencia;

import com.escuelas.project.escuelas_project.persistence.entity.Asistencia;

public record AsistenciaResponsePorClaseDto(
    Long id,
    String contenido,
    String fecha,
    Boolean asistio,
    String alumno
) {
    public AsistenciaResponsePorClaseDto(Asistencia asistencia) {
        this(asistencia.getId_asistecia(), asistencia.getClase().getContenido(), asistencia.getClase().getFechaString(),
                asistencia.getAsistio(), asistencia.getAlumno().getNombre());
    }
}
