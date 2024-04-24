package com.escuelas.project.escuelas_project.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.escuelas.project.escuelas_project.persistence.entity.Alumno;
import com.escuelas.project.escuelas_project.persistence.entity.Curso;
import com.escuelas.project.escuelas_project.service.models.dtos.alumno.AlumnoResponseDto;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    @Query("SELECT new com.escuelas.project.escuelas_project.service.models.dtos.alumno.AlumnoResponseDto(a) FROM Alumno a WHERE a.activo = true")
    public Optional<AlumnoResponseDto> findByNombreActivo(String nombre);

    @Query("SELECT new com.escuelas.project.escuelas_project.service.models.dtos.alumno.AlumnoResponseDto(a) FROM Alumno a WHERE a.activo = true AND a.id_alumno = ?1")
    public Optional<AlumnoResponseDto> findByIdActivo(Long id);

    @Query("SELECT new com.escuelas.project.escuelas_project.service.models.dtos.alumno.AlumnoResponseDto(a) FROM Alumno a WHERE a.activo = true AND a.curso = ?1")
    public List<AlumnoResponseDto> findAllActiveByCurso(Curso curso);

    @Query("SELECT new com.escuelas.project.escuelas_project.service.models.dtos.alumno.AlumnoResponseDto(a) FROM Alumno a WHERE a.activo = true")
    public List<AlumnoResponseDto> findAllActive();

    @Query("SELECT new com.escuelas.project.escuelas_project.service.models.dtos.alumno.AlumnoResponseDto(a) FROM Alumno a")
    public List<AlumnoResponseDto> findAllDto();

} 