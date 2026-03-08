package com.escuelas.project.escuelas_project.asistencia.dtos;

public enum AsistioEnum {
    PRESENTE,
    AUSENTE,
    JUSTIFICADO,
    NOENLISTADO;

    public static AsistioEnum convertToAsistioEnum(String asistencia) {
        return AsistioEnum.valueOf(asistencia.toUpperCase());
    }
}
