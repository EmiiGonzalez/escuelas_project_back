package com.escuelas.project.escuelas_project.clase.entities;

import java.time.LocalDate;

import com.escuelas.project.escuelas_project.curso.entities.Curso;
import com.escuelas.project.escuelas_project.service.utils.Util;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_clase;

    @Temporal(TemporalType.DATE)
    private LocalDate fecha_de_clase;

    private String contenido;

    private Integer numero_de_clase;

    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;


    public Clase(Curso curso, ClaseDto dto) {
        this.curso = curso;
        this.fecha_de_clase = Util.parsearFecha(dto.fecha());
        this.contenido = dto.contenido();
        this.numero_de_clase = dto.numeroDeClase();
        this.activo = true;
    }

    public void update(ClaseUpdateDto dto) {
        if (dto.contenido() != null) {
            this.contenido = dto.contenido();
        }
        if (dto.numeroDeClase() != null) {
            this.numero_de_clase = dto.numeroDeClase();
        }
        if (dto.fecha() != null) {
            LocalDate fechaParseada = Util.parsearFecha(dto.fecha());
            this.setFecha_de_clase(fechaParseada);
        }
    }

    public String getFechaString() {
        return Util.parsearFechaString(this.getFecha_de_clase());
    }

}
