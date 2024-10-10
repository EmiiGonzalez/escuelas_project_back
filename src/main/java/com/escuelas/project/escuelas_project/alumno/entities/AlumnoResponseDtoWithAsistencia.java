package com.escuelas.project.escuelas_project.alumno.entities;

import java.util.List;

import com.escuelas.project.escuelas_project.asistencia.entities.AsistenciaResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AlumnoResponseDtoWithAsistencia {

    Long id;
    String nombre;
    String telefono;
    List <AsistenciaResponseDto> asistencias;
    
    public AlumnoResponseDtoWithAsistencia(Alumno alumno) {
        this.id = alumno.getId_alumno();
        this.nombre = alumno.getNombre();
        this.telefono = alumno.getTelefono();
        this.asistencias = alumno.getAsistencias().stream().map(AsistenciaResponseDto::new).toList();
    }

}
