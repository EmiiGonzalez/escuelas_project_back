package com.escuelas.project.escuelas_project.alumno.dtos;

import java.util.List;
import java.util.UUID;

import com.escuelas.project.escuelas_project.alumno.entities.Alumno;
import com.escuelas.project.escuelas_project.asistencia.dtos.AsistenciaResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AlumnoResponseDtoWithAsistencia {

    UUID id;
    String nombre;
    String telefono;
    List <AsistenciaResponseDto> asistencias;
    
    public AlumnoResponseDtoWithAsistencia(Alumno alumno) {
        this.id = alumno.getId();
        this.nombre = alumno.getNombre();
        this.telefono = alumno.getTelefono();
        this.asistencias = alumno.getAsistencias().stream().map(AsistenciaResponseDto::new).toList();
    }

}
