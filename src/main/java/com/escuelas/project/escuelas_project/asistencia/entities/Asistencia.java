package com.escuelas.project.escuelas_project.asistencia.entities;

import com.escuelas.project.escuelas_project.alumno.entities.Alumno;
import com.escuelas.project.escuelas_project.clase.entities.Clase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "asistencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_asistecia;

    private Boolean asistio;

    @ManyToOne
    @JoinColumn(name = "id_clase")
    private Clase clase;

    @ManyToOne
    @JoinColumn(name = "id_alumno")
    private Alumno alumno;

    public Asistencia(Alumno alumno, Clase clase, AsistenciaDto dto) {
        this.alumno = alumno;
        this.clase = clase;
        this.asistio = dto.asistio();
    }

    public void update(AsistenciaUpdateDto dto) {
        if (dto.asistio() != null) {
            this.asistio = dto.asistio();
        }
    }

    public void update(AsistenciaDto asistencia) {
        if (asistencia.asistio() != null) {
            this.asistio = asistencia.asistio();
        }
    }
}
