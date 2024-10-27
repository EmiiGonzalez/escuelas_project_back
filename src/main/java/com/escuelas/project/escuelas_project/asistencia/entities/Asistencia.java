package com.escuelas.project.escuelas_project.asistencia.entities;

import com.escuelas.project.escuelas_project.alumno.entities.Alumno;
import com.escuelas.project.escuelas_project.clase.entities.Clase;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "asistencias", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id_alumno", "id_clase" })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_asistencia;

    @Enumerated(EnumType.STRING)
    private AsistioEnum asistio;

    @ManyToOne
    @JoinColumn(name = "id_clase", nullable = false)
    private Clase clase;

    @ManyToOne
    @JoinColumn(name = "id_alumno", nullable = false)
    private Alumno alumno;

    public Asistencia(Alumno alumno, Clase clase, AsistenciaDto dto) {
        this.alumno = alumno;
        this.clase = clase;
        this.asistio = AsistioEnum.convertToAsistioEnum(dto.asistio());
    }

    public Asistencia(Alumno alumno, Clase clase, AsistioEnum asistio) {
        this.alumno = alumno;
        this.clase = clase;
        this.asistio = asistio;
    }

    public void update(AsistenciaUpdateDto dto) {
        if (dto.asistio() != null) {
            this.asistio = AsistioEnum.convertToAsistioEnum(dto.asistio());
        }
    }

    public void update(AsistenciaDto asistencia) {
        if (asistencia.asistio() != null) {
            this.asistio = AsistioEnum.convertToAsistioEnum(asistencia.asistio());
        }
    }
}
