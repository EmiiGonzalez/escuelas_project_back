package com.escuelas.project.escuelas_project.service.curso;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.escuelas.project.escuelas_project.persistence.entity.Curso;
import com.escuelas.project.escuelas_project.persistence.entity.Escuela;
import com.escuelas.project.escuelas_project.persistence.repository.CursoRepository;
import com.escuelas.project.escuelas_project.persistence.repository.EscuelaRepository;
import com.escuelas.project.escuelas_project.service.models.dtos.curso.CursoDto;
import com.escuelas.project.escuelas_project.service.models.dtos.curso.CursoDtoUpdate;
import com.escuelas.project.escuelas_project.service.models.dtos.curso.CursoResponseDto;
import com.escuelas.project.escuelas_project.service.models.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.exceptions.cursoExceptions.CursoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.cursoExceptions.CursoNoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.escuelaExceptions.EscuelaNoExistenteException;

import lombok.AllArgsConstructor;

/**
 * Esta clase implementa la interfaz {@link CursoService} y proporciona las
 * operaciones de servicio para las clases.
 *
 * @author Emiliano Gonzalez
 * @version 1.0
 * @since 1.0
 */
@Service
@AllArgsConstructor
public class CursoServiceImp implements CursoService {

    private final CursoRepository cursoRepository;
    private final EscuelaRepository escuelaRepository;

    /**
     * Metodo que busca todos los cursos activos de una escuela dada.
     * @param year el año del curso.
     * @param id el id de la escuela.
     * @return una lista de DTOs de cursos que coinciden con los criterios de busqueda.
     * @throws EntityDisabledException si la escuela esta deshabilitada.
     * @throws EscuelaNoExistenteException si la escuela no existe.
     */
    @Override
    public List<CursoResponseDto> findAllActiveDto(Integer year, Long id) throws EntityDisabledException, EscuelaNoExistenteException {
        Escuela escuelaEntity = this.searchEscuela(id);
        return this.cursoRepository.findAllActiveDto(year, escuelaEntity);
    }

    /**
     * Metodo que busca un curso por su nombre.
     * @param nombre el nombre del curso.
     * @return un DTO de curso.
     * @throws CursoNoExistenteException si el curso no existe.
     */
    @Override
    public CursoResponseDto findByNombreDto(String nombre) throws CursoNoExistenteException {
        Optional<CursoResponseDto> curso = this.cursoRepository.findByNombreDto(nombre);
        curso.orElseThrow(() -> new CursoNoExistenteException("El curso no existe"));

        CursoResponseDto dto = curso.get();
        return dto;
    }

    /**
     * Metodo que guarda un nuevo curso.
     * @param dto el DTO del curso.
     * @param id el id de la escuela.
     * @return un DTO de curso.
     * @throws CursoExistenteException si el curso ya existe.
     * @throws EscuelaNoExistenteException si la escuela no existe.
     * @throws EntityDisabledException si la escuela esta deshabilitada.
     */
    @Override
    public CursoResponseDto save(CursoDto dto, Long id) throws CursoExistenteException, EscuelaNoExistenteException, EntityDisabledException {
        Escuela escuela = searchEscuela(id);

        if (this.cursoRepository.findByNombre(dto.nombre()).isPresent()) {
            throw new CursoExistenteException("El curso ya existe");
        }

        Curso curso = new Curso(dto, escuela);

        return new CursoResponseDto(this.cursoRepository.save(curso));
    }

    /**
     * Metodo que habilita un curso.
     * @param id el id del curso.
     * @throws CursoNoExistenteException si el curso no existe.
     * @throws EntityDisabledException si el curso esta deshabilitado.
     */
    @Override
    public void enable(Long id) throws CursoNoExistenteException, EntityDisabledException {
        Curso curso = searchCurso(id);

        if (curso.getActivo()) {
            throw new EntityDisabledException("El curso ya estava habilitado");
        }

        curso.setActivo(true);
    }

    /**
     * Metodo que deshabilita un curso.
     * @param id el id del curso.
     * @throws CursoNoExistenteException si el curso no existe.
     * @throws EntityDisabledException si el curso esta habilitado.
     */
    @Override
    public void disable(Long id) throws CursoNoExistenteException, EntityDisabledException {
        Curso curso = searchCurso(id);

        if (!curso.getActivo()) {
            throw new EntityDisabledException("El curso ya estava deshabilitado");
        }

        curso.setActivo(false);
    }

    /**
     * Metodo que actualiza un curso.
     * @param dto el DTO del curso.
     * @param id el id del curso.
     * @return un DTO de curso.
     * @throws CursoNoExistenteException si el curso no existe.
     * @throws EntityDisabledException si el curso esta deshabilitado.
     */
    @Override
    public CursoResponseDto update(CursoDtoUpdate dto, Long id) throws CursoNoExistenteException, EntityDisabledException {

        Curso curso = searchCurso(id);
        curso.update(dto);

        return new CursoResponseDto(this.cursoRepository.save(curso));
    }

    /**
     * Metodo privado que busca una escuela por su id.
     * @param id el id de la escuela.
     * @return la entidad escuela.
     * @throws EscuelaNoExistenteException si la escuela no existe.
     * @throws EntityDisabledException si la escuela esta deshabilitada.
     */
    private Curso searchCurso(Long id) throws CursoNoExistenteException{
        return this.cursoRepository.findById(id).orElseThrow(() -> new CursoNoExistenteException("El curso no existe"));
    }

    /**
     * Metodo privado que busca una escuela por su id.
     * @param id el id de la escuela.
     * @return la entidad escuela.
     * @throws EscuelaNoExistenteException si la escuela no existe.
     * @throws EntityDisabledException si la escuela esta deshabilitada.
     */
    private Escuela searchEscuela(Long id) throws EscuelaNoExistenteException, EntityDisabledException{
        Optional<Escuela> escuela = this.escuelaRepository.findById(id);
        escuela.orElseThrow(() -> new EscuelaNoExistenteException("La escuela no existe"));
        Escuela escuelaEntity = escuela.get();

        if (!escuelaEntity.getActivo()) {
            throw new EntityDisabledException("La escuela está deshabilitada");
        }

        return escuelaEntity;
    }
    
}
