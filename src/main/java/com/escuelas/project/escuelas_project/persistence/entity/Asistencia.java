package com.escuelas.project.escuelas_project.persistence.entity;

import com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaDto;
import com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaUpdateDto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_alumno", nullable = false)
    private Alumno alumno;

    @OneToOne
    @JoinColumn(name = "id_clase", nullable = false)
    private Clase clase;

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
}
