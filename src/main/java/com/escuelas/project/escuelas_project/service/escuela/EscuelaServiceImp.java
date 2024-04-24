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

    @Override
    public EscuelaResponseDto save(EscuelaDto escuela) throws EscuelaExistenteException {
        Optional<Escuela> escuelaOptional = escuelaRepository.findByNombre(escuela.nombre());

        if (escuelaOptional.isPresent()) {
            throw new EscuelaExistenteException("La escuela ya existe en la base de datos");
        }

        return new EscuelaResponseDto(escuelaRepository.save(new Escuela(escuela.nombre())));
    }

    @Override
    public EscuelaResponseDto findById(Long id) throws EscuelaNoExistenteException, EntityDisabledException {
        Escuela escuela = searchEscuela(id);
        return new EscuelaResponseDto(escuela);
    }

    @Override
    public EscuelaResponseDto update(EscuelaDtoUpdate escuela, Long id)
            throws EscuelaNoExistenteException, EntityDisabledException {
        Escuela escuelaUpdate = searchEscuela(id);
        escuelaUpdate.update(escuela);
        return new EscuelaResponseDto(escuelaUpdate);
    }

    @Override
    public List<EscuelaResponseDto> findAllActive() {
        return escuelaRepository.findAllActive();
    }

    @Override
    public List<EscuelaResponseDto> findAll() {
        return escuelaRepository.findAll().stream().map(EscuelaResponseDto::new).toList();
    }

    @Override
    public void disable(Long id) throws EscuelaNoExistenteException, EntityDisabledException {
        changeState(id, false);
    }

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
