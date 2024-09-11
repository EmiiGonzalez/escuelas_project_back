package com.escuelas.project.escuelas_project.curso.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.escuelas.project.escuelas_project.curso.entities.Curso;
import com.escuelas.project.escuelas_project.curso.entities.CursoResponseDto;
import com.escuelas.project.escuelas_project.escuela.entities.Escuela;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    
    @Query("SELECT new com.escuelas.project.escuelas_project.curso.entities.CursoResponseDto(c) FROM Curso c WHERE c.activo = true")
    public Optional<CursoResponseDto> findByNombreDto(String nombre);

    @Query("SELECT new com.escuelas.project.escuelas_project.curso.entities.CursoResponseDto(c) FROM Curso c WHERE YEAR(c.fecha_de_curso) = ?1 AND c.escuela = ?2 AND c.activo = true")
    public List<CursoResponseDto> findAllActiveDto(Integer year, Escuela escuela);

    @Query("SELECT c FROM Curso c WHERE c.activo = true AND c.nombre = ?1")
    public Optional<Curso> findByNombreActivo(String nombre);

    @Query("SELECT c FROM Curso c WHERE c.nombre = ?1 AND c.escuela = ?2 AND c.activo = true")
    public Optional<Curso> findByNombre(String nombre, Escuela escuela);

    @Query("SELECT new com.escuelas.project.escuelas_project.curso.entities.CursoResponseDto(c) FROM Curso c WHERE c.activo = true AND c.id_curso = ?1")
    public Optional<CursoResponseDto> findByIdActiveDto(Long id);
}
