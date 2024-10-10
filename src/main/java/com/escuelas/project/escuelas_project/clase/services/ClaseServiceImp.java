package com.escuelas.project.escuelas_project.clase.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.escuelas.project.escuelas_project.clase.entities.Clase;
import com.escuelas.project.escuelas_project.clase.entities.ClaseCountResponseDto;
import com.escuelas.project.escuelas_project.clase.entities.ClaseDto;
import com.escuelas.project.escuelas_project.clase.entities.ClaseResponseDto;
import com.escuelas.project.escuelas_project.clase.entities.ClaseUpdateDto;
import com.escuelas.project.escuelas_project.clase.exceptions.ClaseNoExistenteException;
import com.escuelas.project.escuelas_project.clase.repository.ClaseRepository;
import com.escuelas.project.escuelas_project.curso.entities.Curso;
import com.escuelas.project.escuelas_project.curso.exceptions.CursoNoExistenteException;
import com.escuelas.project.escuelas_project.curso.repository.CursoRepository;
import com.escuelas.project.escuelas_project.service.exceptions.EntityDisabledException;

import lombok.AllArgsConstructor;

/**
 * Esta clase implementa la interfaz {@link ClaseService} y proporciona las
 * operaciones de servicio para las clases.
 *
 * @author Emiliano Gonzalez
 * @version 1.1
 * @since 1.0
 */

@Service
@AllArgsConstructor
public class ClaseServiceImp implements ClaseService {
    private final ClaseRepository claseRepository;
    private final CursoRepository cursoRepository;

    @Override
    public ClaseResponseDto save(ClaseDto dto, Long id)
            throws CursoNoExistenteException, EntityDisabledException {
        Curso curso = searchCurso(id);
        return new ClaseResponseDto(claseRepository.save(new Clase(curso, dto)));
    }

    @Override
    public ClaseResponseDto update(ClaseUpdateDto dto, Long id)
            throws ClaseNoExistenteException, EntityDisabledException {
        Clase clase = searchClase(id);
        clase.update(dto);
        return new ClaseResponseDto(claseRepository.save(clase));
    }

    @Override
    public ClaseResponseDto findById(Long id) throws ClaseNoExistenteException, EntityDisabledException {
        return new ClaseResponseDto(searchClase(id));
    }

    @Override
    public void deleteById(Long id) throws ClaseNoExistenteException, EntityDisabledException {
        Clase clase = searchClase(id);
        clase.setActivo(false);
        claseRepository.save(clase);
    }

    @Override
    public Page<ClaseResponseDto> findAll(Long id, Pageable pageable) throws CursoNoExistenteException, EntityDisabledException {
        Curso curso = searchCurso(id);
        return this.claseRepository.findAllClasesDto(curso, pageable);
    }

    @Override
    public ClaseCountResponseDto count(Long id) throws CursoNoExistenteException, EntityDisabledException {
        Curso curso = searchCurso(id);
        return this.claseRepository.countByCurso(curso).orElse(new ClaseCountResponseDto(0L));
    }

    /**
     * Busca un curso por ID y asegura su disponibilidad.
     *
     * @param id el ID del curso a buscar
     * @return el curso si se encuentra y está activo
     * @throws CursoNoExistenteException si no existe un curso con el ID
     *                                   proporcionado
     * @throws EntityDisabledException   si el curso con el ID proporcionado no está
     *                                   habilitado
     */

    private Curso searchCurso(Long id) throws CursoNoExistenteException, EntityDisabledException {
        Optional<Curso> cursoOptional = this.cursoRepository.findById(id);
        cursoOptional.orElseThrow(() -> new CursoNoExistenteException("El curso no existe"));
        if (!cursoOptional.get().getActivo()) {
            throw new EntityDisabledException("El curso no esta habilitado");
        }

        return cursoOptional.get();
    }

    /**
     * Busca una clase por su ID y lanza una excepción si no existe.
     *
     * @param id el ID de la clase a buscar
     * @return la clase encontrada
     * @throws ClaseNoExistenteException si la clase con el ID dado no existe
     * @throws EntityDisabledException   si el curso con el ID proporcionado no
     *                                   está habilitado
     */
    private Clase searchClase(Long id) throws ClaseNoExistenteException, EntityDisabledException {
        Optional<Clase> claseOptional = this.claseRepository.findById(id);
        claseOptional.orElseThrow(() -> new ClaseNoExistenteException("La clase no existe"));

        if (!claseOptional.get().getCurso().getActivo()) {
            throw new EntityDisabledException("El curso de la clase no esta habilitado");
        }
        return claseOptional.get();
    }

}
