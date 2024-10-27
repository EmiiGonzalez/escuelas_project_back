package com.escuelas.project.escuelas_project.asistencia.entities;

public enum AsistioEnum {
    PRESENTE,
    AUSENTE,
    JUSTIFICADO,
    NOENLISTADO;

    public static AsistioEnum convertToAsistioEnum(String asistencia) {
        return AsistioEnum.valueOf(asistencia.toUpperCase());
    }
}