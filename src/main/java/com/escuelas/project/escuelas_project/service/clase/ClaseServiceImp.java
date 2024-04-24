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
import com.escuelas.project.escuelas_project.service.models.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.exceptions.clasesExceptions.ClaseExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.clasesExceptions.ClaseNoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.cursoExceptions.CursoNoExistenteException;

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
     * @throws EntityDisabledException   si el curso con el ID proporcionado no
     *                                   está habilitado
     */
    @Override
    public ClaseResponseDto save(ClaseDto dto, Long id)
            throws ClaseExistenteException, CursoNoExistenteException, EntityDisabledException {
        Curso curso = searchCurso(id);
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
        Clase clase = searchClase(id);
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
        return new ClaseResponseDto(searchClase(id));
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
        Clase clase = searchClase(id);
        this.claseRepository.delete(clase);
    }

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
    @Override
    public List<ClaseResponseDto> findAll(Long id) throws CursoNoExistenteException, EntityDisabledException {
        Curso curso = searchCurso(id);
        return this.claseRepository.findAllClasesDto(curso);
    }

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
     */
    private Clase searchClase(Long id) throws ClaseNoExistenteException {
        Optional<Clase> claseOptional = this.claseRepository.findById(id);
        claseOptional.orElseThrow(() -> new ClaseNoExistenteException("La clase no existe"));
        return claseOptional.get();
    }

}
