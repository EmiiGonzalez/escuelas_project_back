package com.escuelas.project.escuelas_project.clase.dtos;

public record ClaseCountResponseDto(
    Long count
) {
    public ClaseCountResponseDto(Long count) {
        this.count = count;
    }
}
