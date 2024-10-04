package com.escuelas.project.escuelas_project.asistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.escuelas.project.escuelas_project.alumno.entities.Alumno;
import com.escuelas.project.escuelas_project.asistencia.entities.Asistencia;
import com.escuelas.project.escuelas_project.asistencia.entities.AsistenciaResponseDto;
import com.escuelas.project.escuelas_project.asistencia.entities.AsistenciaResponsePorClaseDto;
import com.escuelas.project.escuelas_project.clase.entities.Clase;

import java.util.List;
import java.util.Optional;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    
    @Query("SELECT new com.escuelas.project.escuelas_project.asistencia.entities.AsistenciaResponseDto(a) FROM Asistencia a WHERE a.alumno = ?1 AND a.clase.activo = true")
    public List<AsistenciaResponseDto> findAllAsistenciaDtoByAlumno(Alumno alumno);

    @Query("SELECT new com.escuelas.project.escuelas_project.asistencia.entities.AsistenciaResponseDto(a) FROM Asistencia a WHERE a.id_asistecia = ?1 AND a.clase.activo = true")
    public Optional<AsistenciaResponseDto> findByIdAsistenciaResponseDto(Long id);

    @Query("SELECT new com.escuelas.project.escuelas_project.asistencia.entities.AsistenciaResponseDto(a) FROM Asistencia a WHERE a.clase = ?1 AND a.clase.activo = true")
    public Optional<AsistenciaResponseDto> findByIdAsistencia(Long id);

    @Query("SELECT new com.escuelas.project.escuelas_project.asistencia.entities.AsistenciaResponsePorClaseDto(a) FROM Asistencia a WHERE a.clase = ?1 AND a.clase.activo = true")
    public List<AsistenciaResponsePorClaseDto> findAllAsistenciaDtoByClase(Clase clase);

    public Optional<Asistencia> findByAlumnoAndClase(Alumno alumno, Clase clase);
}
