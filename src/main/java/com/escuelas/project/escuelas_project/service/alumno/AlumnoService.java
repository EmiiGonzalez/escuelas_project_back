package com.escuelas.project.escuelas_project.service.alumno;

import java.util.List;

import com.escuelas.project.escuelas_project.service.models.dtos.alumno.AlumnoDto;
import com.escuelas.project.escuelas_project.service.models.dtos.alumno.AlumnoDtoUpdate;
import com.escuelas.project.escuelas_project.service.models.dtos.alumno.AlumnoResponseDto;
import com.escuelas.project.escuelas_project.service.models.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.exceptions.alumnoExceptions.AlumnoNoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.cursoExceptions.CursoNoExistenteException;

/**
 * Esta clase define la interfaz {@link AlumnoService} y proporciona las
 * operaciones de servicio para las clases.
 *
 * @author Emiliano Gonzalez
 * @version 1.0
 * @since 1.0
 * 
 * @see AlumnoService
 * @see Alumno
 * @see AlumnoDto
 * @see AlumnoDtoUpdate
 * @see AlumnoResponseDto
 * @see EntityDisabledException
 * @see AlumnoNoExistenteException
 * @see CursoNoExistenteException
 * @see EscuelaNoExistenteException
 * 
 */

public interface AlumnoService {
    /**
     * Crea un nuevo alumno asociado a un curso
     *
     * @param dto AlumnoDto con datos del nuevo alumno
     * @param id  Long con el id del curso
     * @return AlumnoResponseDto con los datos del nuevo alumno
     * @throws CursoNoExistenteException si el curso no existe
     * @throws EntityDisabledException   si el curso o el alumno están
     *                                   deshabilitados
     */
    public AlumnoResponseDto create(AlumnoDto dto, Long id) throws CursoNoExistenteException, EntityDisabledException;

    /**
     * Actualiza los datos de un alumno
     *
     * @param dto AlumnoDtoUpdate con los datos a actualizar
     * @param id  Long con el id del alumno
     * @return AlumnoResponseDto con los datos actualizados
     * @throws AlumnoNoExistenteException si el alumno no existe
     * @throws EntityDisabledException    si el alumno o el curso están
     *                                    deshabilitados
     */
    public AlumnoResponseDto update(AlumnoDtoUpdate dto, Long id)
            throws AlumnoNoExistenteException, EntityDisabledException;

    /**
     * Deshabilita un alumno
     *
     * @param id Long con el id del alumno
     * @throws AlumnoNoExistenteException si el alumno no existe
     * @throws EntityDisabledException    si el alumno o el curso están
     *                                    deshabilitados
     */
    public void disable(Long id) throws AlumnoNoExistenteException, EntityDisabledException;

    /**
     * Habilita un alumno
     *
     * @param id Long con el id del alumno
     * @throws AlumnoNoExistenteException si el alumno no existe
     * @throws EntityDisabledException    si el alumno o el curso están
     *                                    deshabilitados
     */
    public void enable(Long id) throws AlumnoNoExistenteException, EntityDisabledException;

    /**
     * Busca un alumno por su id
     *
     * @param id Long con el id del alumno
     * @return AlumnoResponseDto con los datos del alumno
     * @throws AlumnoNoExistenteException si el alumno no existe
     * @throws EntityDisabledException    si el alumno o el curso están
     *                                    deshabilitados
     */
    public AlumnoResponseDto findById(Long id) throws AlumnoNoExistenteException, EntityDisabledException;

    /**
     * Busca todos los alumnos activos asociados a un curso
     *
     * @param id Long con el id del curso
     * @return List con AlumnoResponseDto de los alumnos activos
     * @throws EntityDisabledException   si el curso está deshabilitado
     * @throws CursoNoExistenteException si el curso no existe
     */
    public List<AlumnoResponseDto> findAllActiveCurso(Long id)
            throws EntityDisabledException, CursoNoExistenteException;

    /**
     * Busca todos los alumnos activos
     *
     * @return List con AlumnoResponseDto de los alumnos activos
     */
    public List<AlumnoResponseDto> findAllActive();

    /**
     * Busca todos los alumnos
     *
     * @return List con AlumnoResponseDto de los alumnos
     */
    public List<AlumnoResponseDto> findAll();
}
