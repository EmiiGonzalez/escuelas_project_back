package com.escuelas.project.escuelas_project.escuela.services;

import java.util.List;

import com.escuelas.project.escuelas_project.escuela.entities.EscuelaDto;
import com.escuelas.project.escuelas_project.escuela.entities.EscuelaDtoUpdate;
import com.escuelas.project.escuelas_project.escuela.entities.EscuelaResponseDto;
import com.escuelas.project.escuelas_project.escuela.exceptions.EscuelaExistenteException;
import com.escuelas.project.escuelas_project.escuela.exceptions.EscuelaNoExistenteException;
import com.escuelas.project.escuelas_project.service.exceptions.EntityDisabledException;

/**
 * Esta clase define la interfaz {@link EscuelaService} y proporciona las
 * operaciones de servicio para las clases.
 *
 * @author Emiliano Gonzalez
 * @version 1.1
 * @since 1.0
 * 
 * @see EscuelaServiceImp
 * @see Escuela
 * @see EscuelaDto
 * @see EscuelaDtoUpdate
 * @see EscuelaResponseDto
 * @see EntityDisabledException
 * @see EscuelaNoExistenteException
 * @see EscuelaExistenteException
 */
public interface EscuelaService {
    /**
     * Guarda una nueva Escuela en la base de datos si aún no existe.
     *
     * @param escuela El objeto EscuelaDto a guardar
     * @return Un DTO de respuesta que contiene la Escuela guardada
     * @throws EscuelaExistenteException si la Escuela ya existe en la base de datos
     */
    EscuelaResponseDto save(EscuelaDto escuela) throws EscuelaExistenteException;

    /**
     * Recupera un objeto EscuelaResponseDto por su ID.
     *
     * @param id el ID de la Escuela a recuperar
     * @return el objeto EscuelaResponseDto que representa la Escuela con el ID dado
     * @throws EscuelaNoExistenteException si la Escuela con el ID dado no existe
     * @throws EntityDisabledException     si la Escuela con el ID dado está
     *                                     deshabilitada
     */
    EscuelaResponseDto findById(Long id) throws EscuelaNoExistenteException, EntityDisabledException;

    /**
     * Actualiza una entidad Escuela con la información proporcionada en
     * EscuelaDtoUpdate y el ID especificado.
     *
     * @param escuela el objeto EscuelaDtoUpdate que contiene la información
     *                actualizada
     * @param id      el ID de la entidad Escuela que se va a actualizar
     * @return un EscuelaResponseDto que representa la entidad Escuela actualizada
     * @throws EscuelaNoExistenteException si no existe una Escuela con el ID
     *                                     proporcionado
     * @throws EntityDisabledException     si la Escuela con el ID proporcionado
     *                                     está deshabilitada
     */
    EscuelaResponseDto update(EscuelaDtoUpdate escuela, Long id)
            throws EscuelaNoExistenteException, EntityDisabledException;

    /**
     * Un método para encontrar todas las Escuelas activas.
     *
     * @return una lista de objetos EscuelaResponseDto activos
     */
    List<EscuelaResponseDto> findAllActive();

    /**
     * Un método para encontrar todas las Escuelas.
     *
     * @return una lista de objetos EscuelaResponseDto activos
     */
    List<EscuelaResponseDto> findAll();

    /**
     * Un método para deshabilitar una Escuela específica cambiando su estado a
     * inactivo.
     *
     * @param id el identificador de la Escuela a deshabilitar
     * @throws EscuelaNoExistenteException si la Escuela no existe
     * @throws EntityDisabledException     si la Escuela ya está deshabilitada
     */
    void disable(Long id) throws EscuelaNoExistenteException, EntityDisabledException;

    /**
     * Un método para habilitar una Escuela específica cambiando su estado a activo.
     *
     * @param id el identificador de la Escuela a habilitar
     * @throws EscuelaNoExistenteException si la Escuela no existe
     * @throws EntityDisabledException     si la Escuela ya está habilitada
     */
    void enable(Long id) throws EscuelaNoExistenteException, EntityDisabledException;

}
