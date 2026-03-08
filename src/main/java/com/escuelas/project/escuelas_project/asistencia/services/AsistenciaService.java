package com.escuelas.project.escuelas_project.asistencia.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.escuelas.project.escuelas_project.alumno.exceptions.AlumnoNoExistenteException;
import com.escuelas.project.escuelas_project.asistencia.entities.Asistencia;
import com.escuelas.project.escuelas_project.asistencia.dtos.AsistenciaDto;
import com.escuelas.project.escuelas_project.asistencia.dtos.AsistenciaResponseDto;
import com.escuelas.project.escuelas_project.asistencia.dtos.AsistenciaResponsePorClaseDto;
import com.escuelas.project.escuelas_project.asistencia.dtos.AsistenciaStats;
import com.escuelas.project.escuelas_project.asistencia.dtos.AsistenciaUpdateDto;
import com.escuelas.project.escuelas_project.asistencia.exceptions.AsistenciaExistenteException;
import com.escuelas.project.escuelas_project.asistencia.exceptions.AsistenciaNoExistenteException;
import com.escuelas.project.escuelas_project.clase.exceptions.ClaseNoExistenteException;
import com.escuelas.project.escuelas_project.escuela.exceptions.EscuelaExistenteException;
import com.escuelas.project.escuelas_project.escuela.exceptions.EscuelaNoExistenteException;
import com.escuelas.project.escuelas_project.service.exceptions.EntityDisabledException;

/**
 * Esta clase define la interfaz {@link AsistenciaService} y proporciona las
 * operaciones de servicio para las clases.
 *
 * @author Emiliano Gonzalez
 * @version 1.0
 * @since 1.0
 * 
 * @see AsistenciaServiceImp
 * @see Asistencia
 * @see AsistenciaDto
 * @see AsistenciaResponseDto
 * @see AsistenciaUpdateDto
 * @see EntityDisabledException
 * @see AlumnoNoExistenteException
 * @see ClaseNoExistenteException
 * @see AsistenciaNoExistenteException
 * @see EscuelaNoExistenteException
 * @see EscuelaExistenteException
 * @see ClaseExistenteException
 * @see AsistenciaExistenteException
 * @see AlumnoExistenteException
 * @see ClaseNoExistenteException
 * 
 */
public interface AsistenciaService {

    /**
     * Crea una asistencia para un alumno en una clase.
     *
     * @param  asistenciaDto  Datos de la asistencia a crear.
     * @param  idClase        Identificador de la clase.
     * @return                Datos de la asistencia creada.
     * @throws AlumnoNoExistenteException Si el alumno no existe.
     * @throws EntityDisabledException   Si la entidad está deshabilitada.
     * @throws ClaseNoExistenteException   Si la clase no existe.
     * @throws AsistenciaExistenteException 
     */
    ArrayList<AsistenciaResponseDto> createAsistencia(List<AsistenciaDto> asistenciaDto, UUID idClase) throws AlumnoNoExistenteException, EntityDisabledException, ClaseNoExistenteException, AsistenciaExistenteException;

    
    /**
     * Actualiza una asistencia.
     *
     * @param  id                 Identificador de la asistencia a actualizar.
     * @param  asistenciaUpdateDto Datos de la asistencia a actualizar.
     * @return                    Datos de la asistencia actualizada.
     * @throws AsistenciaNoExistenteException Si la asistencia no existe.
     * @throws EntityDisabledException      Si la entidad está deshabilitada.
     */
    AsistenciaResponseDto updateAsistencia(UUID id, AsistenciaUpdateDto asistenciaUpdateDto) throws AsistenciaNoExistenteException, EntityDisabledException;

    /**
     * Elimina una asistencia.
     *
     * @param  id  Identificador de la asistencia a eliminar.
     * @throws AsistenciaNoExistenteException Si la asistencia no existe.
     * @throws EntityDisabledException      Si la entidad está deshabilitada.
     */
    void deleteAsistencia(UUID id) throws AsistenciaNoExistenteException, EntityDisabledException; 


    /**
     * Busca una asistencia por su identificador.
     *
     * @param  id  Identificador de la asistencia a buscar.
     * @return     Datos de la asistencia encontrada, si existe.
     * @throws AsistenciaNoExistenteException Si la asistencia no existe.
     * @throws EntityDisabledException      Si la entidad está deshabilitada.
     */
    AsistenciaResponseDto findByIdAsistencia(UUID id) throws AsistenciaNoExistenteException, EntityDisabledException; 

    /**
     * Busca todas las asistencias por su identificador de alumno.
     *
     * @param  id  Identificador del alumno.
     * @return     Lista de datos de las asistencias encontradas, si existen.
     * @throws AlumnoNoExistenteException Si el alumno no existe.
     * @throws EntityDisabledException   Si la entidad está deshabilitada.
     */
    List<AsistenciaResponseDto> findAllAsistenciaDtoByAlumno(UUID id) throws AlumnoNoExistenteException, EntityDisabledException; 

    /**
     * Busca todas las asistencias por su identificador de clase.
     *
     * @param  id  Identificador de la clase.
     * @return     Lista de datos de las asistencias encontradas, si existen.
     * @throws ClaseNoExistenteException   Si la clase no existe.
     * @throws EntityDisabledException     Si la entidad está deshabilitada.
     */
    List<AsistenciaResponsePorClaseDto> findAllAsistenciaDtoByClase(UUID id) throws ClaseNoExistenteException, EntityDisabledException;


    /**
     * 
     * @param id Identificador de la Clase
     * @return  Lista de AsistenciaStats de la Clase
     * @throws ClaseNoExistenteException si la Clase no existe
     * @throws EntityDisabledException si la entidad esta deshabilitada
     */
    List <AsistenciaStats> findAllAsistenciaDtoByClaseStats(UUID id) throws ClaseNoExistenteException, EntityDisabledException;

} 
