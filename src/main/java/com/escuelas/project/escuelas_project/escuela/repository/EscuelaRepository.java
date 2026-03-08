package com.escuelas.project.escuelas_project.escuela.repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.escuelas.project.escuelas_project.escuela.entities.Escuela;
import com.escuelas.project.escuelas_project.escuela.dtos.EscuelaResponseDto;

@Repository
public interface EscuelaRepository extends JpaRepository<Escuela, UUID> {
    
    @Query("SELECT new com.escuelas.project.escuelas_project.escuela.dtos.EscuelaResponseDto(e) FROM Escuela e WHERE e.activo = true")
    public List<EscuelaResponseDto> findAllActive();

    @Query("SELECT e FROM Escuela e WHERE e.activo = true AND e.id = ?1")
    public Optional<Escuela> findByIdActive(UUID id);

    public Optional<Escuela> findByNombre(String nombre);
}
