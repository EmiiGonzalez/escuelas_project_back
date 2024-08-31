package com.escuelas.project.escuelas_project.clase.services;

import java.util.List;

import com.escuelas.project.escuelas_project.clase.entities.ClaseCountResponseDto;
import com.escuelas.project.escuelas_project.clase.entities.ClaseDto;
import com.escuelas.project.escuelas_project.clase.entities.ClaseResponseDto;
import com.escuelas.project.escuelas_project.clase.entities.ClaseUpdateDto;
import com.escuelas.project.escuelas_project.clase.exceptions.ClaseNoExistenteException;
import com.escuelas.project.escuelas_project.curso.exceptions.CursoNoExistenteException;
import com.escuelas.project.escuelas_project.service.exceptions.EntityDisabledException;
/**
 * Esta clase define la interfaz {@link ClaseService} y proporciona las
 * operaciones de servicio para las clases.
 *
 * @author Emiliano Gonzalez
 * @version 1.1
 * @since 1.0
 * 
 * @see ClaseServiceImp
 * @see Clase
 * @see ClaseDto
 * @see ClaseResponseDto
 * @see ClaseUpdateDto
 * @see EntityDisabledException
 * @see ClaseNoExistenteException
 * @see CursoNoExistenteException
 * @see ClaseExistenteException
 * @see ClaseCountResponseDto
 *
 */
public interface ClaseService {

    /**
     * Guarda una nueva clase en la base de datos.
     *
     * @param dto el objeto DTO de la clase a guardar
     * @param id  el ID del curso a asociar a la clase
     * @return el objeto DTO de la clase guardada
     * @throws CursoNoExistenteException si el curso con el ID proporcionado no
     *                                   existe
     * @throws EntityDisabledException   si el curso con el ID proporcionado no
     *                                   está habilitado
     */
    public ClaseResponseDto save(ClaseDto dto, Long id)
            throws CursoNoExistenteException, EntityDisabledException;

    /**
     * Actualiza una clase existente en la base de datos.
     *
     * @param dto el objeto DTO de la clase a actualizar
     * @param id  el ID de la clase a actualizar
     * @return el objeto DTO de la clase actualizada
     * @throws ClaseNoExistenteException si la clase con el ID proporcionado no
     *                                   existe
     * @throws EntityDisabledException
     */
    public ClaseResponseDto update(ClaseUpdateDto dto, Long id)
            throws ClaseNoExistenteException, EntityDisabledException;

    /**
     * Busca una clase por su ID.
     *
     * @param id el ID de la clase a buscar
     * @return el objeto DTO de la clase encontrada
     * @throws ClaseNoExistenteException si la clase con el ID proporcionado no
     *                                   existe
     * @throws EntityDisabledException
     */
    public ClaseResponseDto findById(Long id) throws ClaseNoExistenteException, EntityDisabledException;

    /**
     * Elimina una clase por su ID.
     *
     * @param id el ID de la clase a eliminar
     * @throws ClaseNoExistenteException si la clase con el ID proporcionado no
     *                                   existe
     * @throws EntityDisabledException
     */
    public void deleteById(Long id) throws ClaseNoExistenteException, EntityDisabledException;

    /**
     * Busca todas las clases de un curso por su ID.
     *
     * @param id el ID del curso a buscar
     * @return una lista de objetos DTO de las clases encontradas
     * @throws CursoNoExistenteException si el curso con el ID proporcionado no
     *                                   existe o
     * @throws EntityDisabledException   si el curso con el ID proporcionado
     *                                   no está habilitado
     */
    public List<ClaseResponseDto> findAll(Long id) throws CursoNoExistenteException, EntityDisabledException;

    /**
     * Cuenta el número de clases de un curso por su ID.
     *
     * @param id el ID del curso a contar
     * @return el objeto DTO de conteo de clases encontradas
     * @throws CursoNoExistenteException si el curso con el ID proporcionado no
     *                                   existe
     * @throws EntityDisabledException   si el curso con el ID proporcionado
     *                                   no está habilitado
     */
    public ClaseCountResponseDto count(Long id) throws CursoNoExistenteException, EntityDisabledException;

}
