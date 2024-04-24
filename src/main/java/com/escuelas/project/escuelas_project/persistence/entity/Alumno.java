package com.escuelas.project.escuelas_project.persistence.entity;

import com.escuelas.project.escuelas_project.service.models.dtos.alumno.AlumnoDto;
import com.escuelas.project.escuelas_project.service.models.dtos.alumno.AlumnoDtoUpdate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Table(name = "alumnos")
@Entity
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_alumno;

    private String nombre;
    private String telefono;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Curso curso;

    private Boolean activo;

    public Alumno(AlumnoDto dto, Curso curso) {
        this.nombre = dto.nombre();
        if (dto.telefono() != null) {
            this.telefono = dto.telefono().trim();
        }
        this.curso = curso;
        this.activo = true;
    }

    public Alumno() {
        this.activo = true;
    }

    public void update(AlumnoDtoUpdate dto) {
        if (dto.nombre() != null) {
            this.setNombre(dto.nombre());
        }
    }

    public void disable() {
        this.activo = false;
    }

    public void enable() {
        this.activo = true;
    }
}
