package com.escuelas.project.escuelas_project.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.escuelas.project.escuelas_project.persistence.entity.Clase;
import com.escuelas.project.escuelas_project.persistence.entity.Curso;
import com.escuelas.project.escuelas_project.service.models.dtos.clase.ClaseCountResponseDto;
import com.escuelas.project.escuelas_project.service.models.dtos.clase.ClaseResponseDto;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {

    @Query("SELECT new com.escuelas.project.escuelas_project.service.models.dtos.clase.ClaseResponseDto(c) FROM Clase c WHERE c.curso = ?1")
    public List<ClaseResponseDto> findAllClasesDto(Curso curso);

    @Query("SELECT new com.escuelas.project.escuelas_project.service.models.dtos.clase.ClaseResponseDto(c) FROM Clase c WHERE c.id_clase = ?1")
    public Optional<ClaseResponseDto> findByIdClaseResponseDto(Long id);

    @Query("SELECT new com.escuelas.project.escuelas_project.service.models.dtos.clase.ClaseCountResponseDto(COUNT(c)) FROM Clase c WHERE c.curso = ?1")
    public Optional<ClaseCountResponseDto> countByCurso(Curso curso);

}