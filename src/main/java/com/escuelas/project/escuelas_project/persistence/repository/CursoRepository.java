package com.escuelas.project.escuelas_project.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.escuelas.project.escuelas_project.persistence.entity.Curso;
import com.escuelas.project.escuelas_project.persistence.entity.Escuela;
import com.escuelas.project.escuelas_project.service.models.dtos.curso.CursoResponseDto;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    
    @Query("SELECT new com.escuelas.project.escuelas_project.service.models.dtos.curso.CursoResponseDto(c) FROM Curso c WHERE c.activo = true")
    public Optional<CursoResponseDto> findByNombreDto(String nombre);

    @Query("SELECT new com.escuelas.project.escuelas_project.service.models.dtos.curso.CursoResponseDto(c) FROM Curso c WHERE YEAR(c.fecha_de_curso) = ?1 AND c.escuela = ?2 AND c.activo = true")
    public List<CursoResponseDto> findAllActiveDto(Integer year, Escuela escuela);

    @Query("SELECT c FROM Curso c WHERE c.activo = true AND c.nombre = ?1")
    public Optional<Curso> findByNombreActivo(String nombre);

    public Optional<Curso> findByNombre(String nombre);

}
