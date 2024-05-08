package com.escuelas.project.escuelas_project.service.asistencia;

import java.util.ArrayList;
import java.util.List;
import com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaDto;
import com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaResponseDto;
import com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaResponsePorClaseDto;
import com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaUpdateDto;
import com.escuelas.project.escuelas_project.service.models.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.exceptions.alumnoExceptions.AlumnoNoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.asistenciaExceptions.AsistenciaNoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.clasesExceptions.ClaseNoExistenteException;

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
     */
    ArrayList<AsistenciaResponseDto> createAsistencia(List<AsistenciaDto> asistenciaDto, Long idClase) throws AlumnoNoExistenteException, EntityDisabledException, ClaseNoExistenteException;

    
    /**
     * Actualiza una asistencia.
     *
     * @param  id                 Identificador de la asistencia a actualizar.
     * @param  asistenciaUpdateDto Datos de la asistencia a actualizar.
     * @return                    Datos de la asistencia actualizada.
     * @throws AsistenciaNoExistenteException Si la asistencia no existe.
     * @throws EntityDisabledException      Si la entidad está deshabilitada.
     */
    AsistenciaResponseDto updateAsistencia(Long id, AsistenciaUpdateDto asistenciaUpdateDto) throws AsistenciaNoExistenteException, EntityDisabledException;

    /**
     * Elimina una asistencia.
     *
     * @param  id  Identificador de la asistencia a eliminar.
     * @throws AsistenciaNoExistenteException Si la asistencia no existe.
     * @throws EntityDisabledException      Si la entidad está deshabilitada.
     */
    void deleteAsistencia(Long id) throws AsistenciaNoExistenteException, EntityDisabledException; 


    /**
     * Busca una asistencia por su identificador.
     *
     * @param  id  Identificador de la asistencia a buscar.
     * @return     Datos de la asistencia encontrada, si existe.
     * @throws AsistenciaNoExistenteException Si la asistencia no existe.
     * @throws EntityDisabledException      Si la entidad está deshabilitada.
     */
    AsistenciaResponseDto findByIdAsistencia(Long id) throws AsistenciaNoExistenteException, EntityDisabledException; 

    /**
     * Busca todas las asistencias por su identificador de alumno.
     *
     * @param  id  Identificador del alumno.
     * @return     Lista de datos de las asistencias encontradas, si existen.
     * @throws AlumnoNoExistenteException Si el alumno no existe.
     * @throws EntityDisabledException   Si la entidad está deshabilitada.
     */
    List<AsistenciaResponseDto> findAllAsistenciaDtoByAlumno(Long id) throws AlumnoNoExistenteException, EntityDisabledException; 

    /**
     * Busca todas las asistencias por su identificador de clase.
     *
     * @param  id  Identificador de la clase.
     * @return     Lista de datos de las asistencias encontradas, si existen.
     * @throws ClaseNoExistenteException   Si la clase no existe.
     * @throws EntityDisabledException     Si la entidad está deshabilitada.
     */
    List<AsistenciaResponsePorClaseDto> findAllAsistenciaDtoByClase(Long id) throws ClaseNoExistenteException, EntityDisabledException;

} 