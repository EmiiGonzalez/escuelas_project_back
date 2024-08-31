package com.escuelas.project.escuelas_project.asistencia.entities;

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
