package com.escuelas.project.escuelas_project.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.escuelas.project.escuelas_project.persistence.entity.Asistencia;
import com.escuelas.project.escuelas_project.persistence.entity.Clase;
import com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaResponseDto;
import com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaResponsePorClaseDto;
import com.escuelas.project.escuelas_project.persistence.entity.Alumno;
import java.util.List;
import java.util.Optional;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    
    @Query("SELECT new com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaResponseDto(a) FROM Asistencia a WHERE a.alumno = ?1")
    public List<AsistenciaResponseDto> findAllAsistenciaDtoByAlumno(Alumno alumno);

    @Query("SELECT new com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaResponseDto(a) FROM Asistencia a WHERE a.id_asistecia = ?1")
    public Optional<AsistenciaResponseDto> findByIdAsistenciaResponseDto(Long id);

    @Query("SELECT new com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaResponseDto(a) FROM Asistencia a WHERE a.clase = ?1")
    public Optional<AsistenciaResponseDto> findByIdAsistencia(Long id);

    @Query("SELECT new com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaResponsePorClaseDto(a) FROM Asistencia a WHERE a.clase = ?1")
    public List<AsistenciaResponsePorClaseDto> findAllAsistenciaDtoByClase(Clase clase);

}
