package com.escuelas.project.escuelas_project.service.escuela;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.escuelas.project.escuelas_project.persistence.entity.Escuela;
import com.escuelas.project.escuelas_project.persistence.repository.EscuelaRepository;
import com.escuelas.project.escuelas_project.service.models.dtos.escuela.EscuelaDto;
import com.escuelas.project.escuelas_project.service.models.dtos.escuela.EscuelaDtoUpdate;
import com.escuelas.project.escuelas_project.service.models.dtos.escuela.EscuelaResponseDto;
import com.escuelas.project.escuelas_project.service.models.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.exceptions.escuelaExceptions.EscuelaExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.escuelaExceptions.EscuelaNoExistenteException;

import lombok.AllArgsConstructor;

/**
 * Esta clase implementa la interfaz {@link EscuelaService} y proporciona las
 * operaciones de servicio para las clases.
 *
 * @author Emiliano Gonzalez
 * @version 1.1
 * @since 1.0
 */
@Service
@AllArgsConstructor
public class EscuelaServiceImp implements EscuelaService {

    private final EscuelaRepository escuelaRepository;

    /**
     * Guarda una nueva Escuela en la base de datos si aún no existe.
     *
     * @param escuela El objeto EscuelaDto a guardar
     * @return Un DTO de respuesta que contiene la Escuela guardada
     * @throws EscuelaExistenteException si la Escuela ya existe en la base de datos
     */
    @Override
    public EscuelaResponseDto save(EscuelaDto escuela) throws EscuelaExistenteException {
        Optional<Escuela> escuelaOptional = escuelaRepository.findByNombre(escuela.nombre());

        if (escuelaOptional.isPresent()) {
            throw new EscuelaExistenteException("La escuela ya existe en la base de datos");
        }

        return new EscuelaResponseDto(escuelaRepository.save(new Escuela(escuela.nombre())));
    }

    /**
     * Recupera un objeto EscuelaResponseDto por su ID.
     *
     * @param id el ID de la Escuela a recuperar
     * @return el objeto EscuelaResponseDto que representa la Escuela con el ID dado
     * @throws EscuelaNoExistenteException si la Escuela con el ID dado no existe
     * @throws EntityDisabledException     si la Escuela con el ID dado está
     *                                     deshabilitada
     */
    @Override
    public EscuelaResponseDto findById(Long id) throws EscuelaNoExistenteException, EntityDisabledException {
        Escuela escuela = searchEscuela(id);
        return new EscuelaResponseDto(escuela);
    }

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
    @Override
    public EscuelaResponseDto update(EscuelaDtoUpdate escuela, Long id)
            throws EscuelaNoExistenteException, EntityDisabledException {
        Escuela escuelaUpdate = searchEscuela(id);
        escuelaUpdate.update(escuela);
        return new EscuelaResponseDto(escuelaUpdate);
    }

    /**
     * Un método para encontrar todas las Escuelas activas.
     *
     * @return una lista de objetos EscuelaResponseDto activos
     */
    @Override
    public List<EscuelaResponseDto> findAllActive() {
        return escuelaRepository.findAllActive();
    }

    /**
     * Un método para encontrar todas las Escuelas.
     *
     * @return una lista de objetos EscuelaResponseDto activos
     */
    @Override
    public List<EscuelaResponseDto> findAll() {
        return escuelaRepository.findAll().stream().map(EscuelaResponseDto::new).toList();
    }

    /**
     * Un método para deshabilitar una Escuela específica cambiando su estado a
     * inactivo.
     *
     * @param id el identificador de la Escuela a deshabilitar
     * @throws EscuelaNoExistenteException si la Escuela no existe
     * @throws EntityDisabledException     si la Escuela ya está deshabilitada
     */
    @Override
    public void disable(Long id) throws EscuelaNoExistenteException, EntityDisabledException {
        changeState(id, false);
    }

    /**
     * Un método para habilitar una Escuela específica cambiando su estado a activo.
     *
     * @param id el identificador de la Escuela a habilitar
     * @throws EscuelaNoExistenteException si la Escuela no existe
     * @throws EntityDisabledException     si la Escuela ya está habilitada
     */
    @Override
    public void enable(Long id) throws EscuelaNoExistenteException, EntityDisabledException {
        changeState(id, true);
    }

    /**
     * Busca una Escuela por su ID y lanza excepciones si no existe o está
     * deshabilitada.
     *
     * @param id el ID de la Escuela a buscar
     * @return el objeto Escuela encontrado
     * @throws EscuelaNoExistenteException si la Escuela no existe en la base de
     *                                     datos
     * @throws EntityDisabledException     si la Escuela está deshabilitada
     */
    private Escuela searchEscuela(Long id) throws EscuelaNoExistenteException, EntityDisabledException {
        Optional<Escuela> escuelaOptional = escuelaRepository.findByIdActive(id);
        escuelaOptional.orElseThrow(() -> new EscuelaNoExistenteException("La escuela no existe en la base de datos"));

        Escuela escuela = escuelaOptional.get();
        if (!escuela.getActivo()) {
            throw new EntityDisabledException("La escuela esta deshabilitada");
        }
        return escuela;
    }

    /**
     * Cambia el estado de una Escuela con el ID proporcionado.
     *
     * @param id    el ID de la Escuela cuyo estado se va a cambiar
     * @param state el nuevo estado de la Escuela (verdadero para habilitado, falso
     *              para deshabilitado)
     * @throws EntityDisabledException     si la Escuela ya está en el estado
     *                                     deseado
     * @throws EscuelaNoExistenteException si la Escuela no existe en la base de
     *                                     datos
     */
    private void changeState(Long id, Boolean state) throws EntityDisabledException, EscuelaNoExistenteException {
        Optional<Escuela> escuelaOptional = escuelaRepository.findById(id);
        if (!escuelaOptional.isPresent()) {
            throw new EscuelaNoExistenteException("La escuela no existe en la base de datos");
        }
        Escuela escuela = escuelaOptional.get();
        if (!state) {
            if (!escuela.getActivo()) {
                throw new EntityDisabledException("La escuela ya estaba deshabilitada");
            }
            escuela.setActivo(state);
        }
        if (state) {
            if (escuela.getActivo()) {
                throw new EntityDisabledException("La escuela ya estaba habilitada");
            }
            escuela.setActivo(state);
        }
    }

}
