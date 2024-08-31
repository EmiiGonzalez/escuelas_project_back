package com.escuelas.project.escuelas_project.curso.services;

import java.util.List;

import com.escuelas.project.escuelas_project.curso.entities.CursoDto;
import com.escuelas.project.escuelas_project.curso.entities.CursoDtoUpdate;
import com.escuelas.project.escuelas_project.curso.entities.CursoResponseDto;
import com.escuelas.project.escuelas_project.curso.exceptions.CursoExistenteException;
import com.escuelas.project.escuelas_project.curso.exceptions.CursoNoExistenteException;
import com.escuelas.project.escuelas_project.escuela.exceptions.EscuelaNoExistenteException;
import com.escuelas.project.escuelas_project.service.exceptions.EntityDisabledException;

/**
 * Esta clase define los metodos de servicio para la clase {@link Curso}.
 * 
 * @author Emiliano Gonzalez
 * @version 1.0
 * @since 1.0
 * 
 * @see Curso
 * @see CursoDto
 * @see CursoDtoUpdate
 * @see EntityDisabledException
 * @see EscuelaNoExistenteException
 * @see CursoNoExistenteException
 * @see CursoExistenteException
 */
public interface CursoService {
    /**
     * Metodo que busca todos los cursos activos de una escuela dada.
     * 
     * @param year el año del curso.
     * @param id   el id de la escuela.
     * @return una lista de DTOs de cursos que coinciden con los criterios de
     *         busqueda.
     * @throws EntityDisabledException     si la escuela esta deshabilitada.
     * @throws EscuelaNoExistenteException si la escuela no existe.
     */
    List<CursoResponseDto> findAllActiveDto(Integer year, Long id)
            throws EntityDisabledException, EscuelaNoExistenteException;


    /**
     * Metodo que guarda un nuevo curso.
     * 
     * @param dto el DTO del curso.
     * @param id  el id de la escuela.
     * @return un DTO de curso.
     * @throws CursoExistenteException     si el curso ya existe.
     * @throws EscuelaNoExistenteException si la escuela no existe.
     * @throws EntityDisabledException     si la escuela esta deshabilitada.
     */
    CursoResponseDto save(CursoDto dto, Long id)
            throws CursoExistenteException, EscuelaNoExistenteException, EntityDisabledException;

    /**
     * Metodo que habilita un curso.
     * 
     * @param id el id del curso.
     * @throws CursoNoExistenteException si el curso no existe.
     * @throws EntityDisabledException   si el curso esta deshabilitado.
     */
    void enable(Long id) throws CursoNoExistenteException, EntityDisabledException;

    /**
     * Metodo que deshabilita un curso.
     * 
     * @param id el id del curso.
     * @throws CursoNoExistenteException si el curso no existe.
     * @throws EntityDisabledException   si el curso esta habilitado.
     */
    void disable(Long id) throws CursoNoExistenteException, EntityDisabledException;

    /**
     * Metodo que actualiza un curso.
     * 
     * @param dto el DTO del curso.
     * @param id  el id del curso.
     * @return un DTO de curso.
     * @throws CursoNoExistenteException si el curso no existe.
     * @throws EntityDisabledException   si el curso esta deshabilitado.
     */
    CursoResponseDto update(CursoDtoUpdate dto, Long id) throws CursoNoExistenteException, EntityDisabledException;
}
