package com.escuelas.project.escuelas_project.persistence.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.escuelas.project.escuelas_project.persistence.entity.Escuela;
import com.escuelas.project.escuelas_project.service.models.dtos.escuela.EscuelaResponseDto;
@Repository
public interface EscuelaRepository extends JpaRepository<Escuela, Long> {
    
    @Query("SELECT new com.escuelas.project.escuelas_project.service.models.dtos.escuela.EscuelaResponseDto(e) FROM Escuela e WHERE e.activo = true")
    public List<EscuelaResponseDto> findAllActive();

    @Query("SELECT e FROM Escuela e WHERE e.activo = true AND e.id_escuela = ?1")
    public Optional<Escuela> findByIdActive(Long id);

    public Optional<Escuela> findByNombre(String nombre);
}
