package com.escuelas.project.escuelas_project.asistencia.entities;

public record AsistenciaResponseDto(
        Long id,
        String contenido,
        String fecha,
        Boolean asistio) {
    public AsistenciaResponseDto(Asistencia asistencia) {
        this(asistencia.getId_asistecia(), asistencia.getClase().getContenido(), asistencia.getClase().getFechaString(),
                asistencia.getAsistio());
    }
}
