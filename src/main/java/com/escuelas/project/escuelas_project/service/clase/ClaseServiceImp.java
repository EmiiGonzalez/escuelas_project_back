package com.escuelas.project.escuelas_project.service.clase;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.escuelas.project.escuelas_project.persistence.entity.Clase;
import com.escuelas.project.escuelas_project.persistence.entity.Curso;
import com.escuelas.project.escuelas_project.persistence.repository.ClaseRepository;
import com.escuelas.project.escuelas_project.persistence.repository.CursoRepository;
import com.escuelas.project.escuelas_project.service.models.dtos.clase.ClaseCountResponseDto;
import com.escuelas.project.escuelas_project.service.models.dtos.clase.ClaseDto;
import com.escuelas.project.escuelas_project.service.models.dtos.clase.ClaseResponseDto;
import com.escuelas.project.escuelas_project.service.models.dtos.clase.ClaseUpdateDto;
import com.escuelas.project.escuelas_project.service.models.exceptions.clasesExceptions.ClaseExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.clasesExceptions.ClaseNoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.cursoExceptions.CursoNoExistenteException;

import lombok.AllArgsConstructor;

/**
 * Esta clase implementa la interfaz {@link ClaseService} y proporciona las
 * operaciones de servicio para las clases.
 *
 * @author Emiliano Gonzalez
 * @version 1.0
 * @since 1.0
 */

@Service
@AllArgsConstructor
public class ClaseServiceImp implements ClaseService {
    private final ClaseRepository claseRepository;
    private final CursoRepository cursoRepository;

    /**
     * Guarda una nueva clase en la base de datos.
     *
     * @param dto el objeto DTO de la clase a guardar
     * @param id  el ID del curso a asociar a la clase
     * @return el objeto DTO de la clase guardada
     * @throws ClaseExistenteException   si ya existe una clase para el curso
     *                                   proporcionado
     * @throws CursoNoExistenteException si el curso con el ID proporcionado no
     *                                   existe
     */
    @Override
    public ClaseResponseDto save(ClaseDto dto, Long id) throws ClaseExistenteException, CursoNoExistenteException {
        Optional<Curso> cursoOptional = this.cursoRepository.findById(id);
        if (!cursoOptional.isPresent()) {
            throw new CursoNoExistenteException("El curso no existe");
        }
        Curso curso = cursoOptional.get();

        return new ClaseResponseDto(claseRepository.save(new Clase(curso, dto)));
    }

    /**
     * Actualiza una clase existente en la base de datos.
     *
     * @param dto el objeto DTO de la clase a actualizar
     * @param id  el ID de la clase a actualizar
     * @return el objeto DTO de la clase actualizada
     * @throws ClaseNoExistenteException si la clase con el ID proporcionado no
     *                                   existe
     */
    @Override
    public ClaseResponseDto update(ClaseUpdateDto dto, Long id) throws ClaseNoExistenteException {
        Optional<Clase> claseOptional = this.claseRepository.findById(id);
        if (claseOptional.isEmpty()) {
            throw new ClaseNoExistenteException("La clase no existe");
        }
        Clase clase = claseOptional.get();
        clase.update(dto);
        return new ClaseResponseDto(claseRepository.save(clase));
    }

    /**
     * Busca una clase por su ID.
     *
     * @param id el ID de la clase a buscar
     * @return el objeto DTO de la clase encontrada
     * @throws ClaseNoExistenteException si la clase con el ID proporcionado no
     *                                   existe
     */
    @Override
    public ClaseResponseDto findById(Long id) throws ClaseNoExistenteException {
        Optional<Clase> claseOptional = this.claseRepository.findById(id);
        if (claseOptional.isEmpty()) {
            throw new ClaseNoExistenteException("La clase no existe");
        }
        return new ClaseResponseDto(claseOptional.get());
    }

    /**
     * Elimina una clase por su ID.
     *
     * @param id el ID de la clase a eliminar
     * @throws ClaseNoExistenteException si la clase con el ID proporcionado no
     *                                   existe
     */
    @Override
    public void deleteById(Long id) throws ClaseNoExistenteException {
        Optional<Clase> claseOptional = this.claseRepository.findById(id);
        if (claseOptional.isEmpty()) {
            throw new ClaseNoExistenteException("La clase no existe");
        }
        this.claseRepository.delete(claseOptional.get());
    }

    /**
     * Busca todas las clases de un curso por su ID.
     *
     * @param id el ID del curso a buscar
     * @return una lista de objetos DTO de las clases encontradas
     * @throws CursoNoExistenteException si el curso con el ID proporcionado no
     *                                   existe o no está habilitado
     */
    @Override
    public List<ClaseResponseDto> findAll(Long id) throws CursoNoExistenteException {
        Optional<Curso> cursoOptional = this.cursoRepository.findById(id);
        if (cursoOptional.isEmpty() || !cursoOptional.get().getActivo()) {
            throw new CursoNoExistenteException("El curso no existe o no esta habilitado");
        }
        Curso curso = cursoOptional.get();
        return this.claseRepository.findAllClasesDto(curso);
    }

    /**
     * Cuenta el número de clases de un curso por su ID.
     *
     * @param id el ID del curso a contar
     * @return el objeto DTO de conteo de clases encontradas
     * @throws CursoNoExistenteException si el curso con el ID proporcionado no
     *                                   existe o no está habilitado
     */
    @Override
    public ClaseCountResponseDto count(Long id) throws CursoNoExistenteException {
        Optional<Curso> cursoOptional = this.cursoRepository.findById(id);
        if (cursoOptional.isEmpty() || !cursoOptional.get().getActivo()) {
            throw new CursoNoExistenteException("El curso no existe o no esta habilitado");
        }

        Curso curso = cursoOptional.get();

        return this.claseRepository.countByCurso(curso).orElse(new ClaseCountResponseDto(0L));
    }

}
