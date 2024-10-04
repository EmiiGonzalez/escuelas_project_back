package com.escuelas.project.escuelas_project.clase.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.escuelas.project.escuelas_project.clase.entities.Clase;
import com.escuelas.project.escuelas_project.clase.entities.ClaseCountResponseDto;
import com.escuelas.project.escuelas_project.clase.entities.ClaseResponseDto;
import com.escuelas.project.escuelas_project.curso.entities.Curso;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {

    @Query("SELECT new com.escuelas.project.escuelas_project.clase.entities.ClaseResponseDto(c) FROM Clase c WHERE c.curso = ?1 AND c.activo = true")
    public Page<ClaseResponseDto> findAllClasesDto(Curso curso, Pageable pageable);

    @Query("SELECT new com.escuelas.project.escuelas_project.clase.entities.ClaseResponseDto(c) FROM Clase c WHERE c.id_clase = ?1 AND c.activo = true")
    public Optional<ClaseResponseDto> findByIdClaseResponseDto(Long id);

    @Query("SELECT new com.escuelas.project.escuelas_project.clase.entities.ClaseCountResponseDto(COUNT(c)) FROM Clase c WHERE c.curso = ?1 AND c.activo = true")
    public Optional<ClaseCountResponseDto> countByCurso(Curso curso);

}