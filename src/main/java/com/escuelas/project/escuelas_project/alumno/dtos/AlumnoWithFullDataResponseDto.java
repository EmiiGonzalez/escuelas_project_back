package com.escuelas.project.escuelas_project.alumno.dtos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.escuelas.project.escuelas_project.alumno.entities.Alumno;
import com.escuelas.project.escuelas_project.asistencia.dtos.AsistenciaResponseDto;
import com.escuelas.project.escuelas_project.asistencia.dtos.AsistenciaStats;
import com.escuelas.project.escuelas_project.asistencia.dtos.AsistioEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase para representar un alumno con sus datos completos.
 * 
 * @author Emiliano Gonzalez
 * @version 1.0
 * @since 1.0
 * 
 * @see Alumno
 * @see AlumnoResponseDto
 * @see AsistenciaStats
 * @see AsistioEnum
 * @see AlumnoWithFullDataResponseDto
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlumnoWithFullDataResponseDto {

    private AlumnoResponseDto alumno;
    private UUID idCurso;
    private List<AsistenciaResponseDto> asistencias;
    private List<AsistenciaStats> stats;

    public AlumnoWithFullDataResponseDto(Alumno alumno) {
        this.idCurso = alumno.getCurso().getId();
        this.alumno = new AlumnoResponseDto(alumno);

        this.asistencias = new ArrayList<>(
            alumno.getAsistencias().stream().map(AsistenciaResponseDto::new).toList()
        );

        HashMap<AsistioEnum, AsistenciaStats> asistenciasStats = new HashMap<>();
        alumno.getAsistencias()
                .forEach(a -> asistenciasStats.computeIfAbsent(a.getAsistio(), AsistenciaStats::new).updateTotal());

        this.stats = new ArrayList<>(asistenciasStats.values());
    }
}
