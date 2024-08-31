package com.escuelas.project.escuelas_project.alumno.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.escuelas.project.escuelas_project.alumno.entities.Alumno;
import com.escuelas.project.escuelas_project.alumno.entities.AlumnoResponseDto;
import com.escuelas.project.escuelas_project.curso.entities.Curso;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    @Query("SELECT new com.escuelas.project.escuelas_project.alumno.entities.AlumnoResponseDto(a) FROM Alumno a WHERE a.activo = true")
    public Optional<AlumnoResponseDto> findByNombreActivo(String nombre);

    @Query("SELECT new com.escuelas.project.escuelas_project.alumno.entities.AlumnoResponseDto(a) FROM Alumno a WHERE a.activo = true AND a.id_alumno = ?1")
    public Optional<AlumnoResponseDto> findByIdActivo(Long id);

    @Query("SELECT new com.escuelas.project.escuelas_project.alumno.entities.AlumnoResponseDto(a) FROM Alumno a WHERE a.activo = true AND a.curso = ?1")
    public List<AlumnoResponseDto> findAllActiveByCurso(Curso curso);

    @Query("SELECT new com.escuelas.project.escuelas_project.alumno.entities.AlumnoResponseDto(a) FROM Alumno a WHERE a.activo = true")
    public List<AlumnoResponseDto> findAllActive();

    @Query("SELECT new com.escuelas.project.escuelas_project.alumno.entities.AlumnoResponseDto(a) FROM Alumno a")
    public List<AlumnoResponseDto> findAllDto();

} 