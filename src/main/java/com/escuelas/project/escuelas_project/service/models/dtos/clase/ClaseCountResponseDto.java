package com.escuelas.project.escuelas_project.service.models.dtos.clase;

public record ClaseCountResponseDto(
    Long count
) {
    public ClaseCountResponseDto(Long count) {
        this.count = count;
    }
}
