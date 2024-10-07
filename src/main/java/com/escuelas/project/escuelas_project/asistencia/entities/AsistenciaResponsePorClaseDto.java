package com.escuelas.project.escuelas_project.asistencia.entities;

public record AsistenciaResponsePorClaseDto(
    Long id,
    Boolean asistio,
    String alumno
) {
    public AsistenciaResponsePorClaseDto(Asistencia asistencia) {
        this( asistencia.getId_asistecia(), asistencia.getAsistio(), asistencia.getAlumno().getNombre() );
    }
}
