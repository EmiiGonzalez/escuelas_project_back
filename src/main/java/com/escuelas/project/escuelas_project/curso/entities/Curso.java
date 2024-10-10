package com.escuelas.project.escuelas_project.curso.entities;

import java.time.LocalDate;
import java.util.List;

import com.escuelas.project.escuelas_project.alumno.entities.Alumno;
import com.escuelas.project.escuelas_project.escuela.entities.Escuela;
import com.escuelas.project.escuelas_project.service.utils.Util;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cursos")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_curso;
    private String nombre;

    private String materia;

    @Temporal(TemporalType.DATE)
    private LocalDate fecha_de_curso;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_escuela", nullable = false)
    private Escuela escuela;

    private Boolean activo;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Alumno> alumnos;


    public Curso(CursoDto dto, Escuela escuela) {
        this.nombre = dto.nombre();
        LocalDate fechaParseada = Util.parsearFecha(dto.fecha());
        this.fecha_de_curso = fechaParseada;
        this.escuela = escuela;
        this.materia = dto.materia();
        this.activo = true;
    }

    public void update(CursoDtoUpdate curso) {
        if (curso.nombre() != null) {
            this.setNombre(curso.nombre());
        }
        if (curso.fecha() != null) {
            LocalDate fechaParseada = Util.parsearFecha(curso.fecha());
            this.setFecha_de_curso(fechaParseada);
        }

        if (curso.materia() != null) {
            this.setMateria(curso.materia());
        }
    }

}
