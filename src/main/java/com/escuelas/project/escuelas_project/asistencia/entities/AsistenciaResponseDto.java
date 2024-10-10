package com.escuelas.project.escuelas_project.asistencia.entities;

public record AsistenciaResponseDto(
        Long id,
        Integer claseNumero,
        String fecha,
        Boolean asistio) {
    public AsistenciaResponseDto(Asistencia asistencia) {
        this(asistencia.getId_asistencia(), asistencia.getClase().getNumero_de_clase(), asistencia.getClase().getFechaString(), asistencia.getAsistio());
    }
}
