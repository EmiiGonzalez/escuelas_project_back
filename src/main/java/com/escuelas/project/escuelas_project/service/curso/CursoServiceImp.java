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

    @Override
    public List<CursoResponseDto> findAllActiveDto(Integer year, Long id)
            throws EntityDisabledException, EscuelaNoExistenteException {
        Escuela escuelaEntity = this.searchEscuela(id);
        return this.cursoRepository.findAllActiveDto(year, escuelaEntity);
    }

    @Override
    public CursoResponseDto findByNombreDto(String nombre) throws CursoNoExistenteException, EntityDisabledException {
        CursoResponseDto dto = new CursoResponseDto(searchCurso(nombre));
        return dto;
    }

    @Override
    public CursoResponseDto save(CursoDto dto, Long id)
            throws CursoExistenteException, EscuelaNoExistenteException, EntityDisabledException {
        Escuela escuela = searchEscuela(id);

        if (this.cursoRepository.findByNombre(dto.nombre()).isPresent()) {
            throw new CursoExistenteException("El curso ya existe");
        }

        Curso curso = new Curso(dto, escuela);

        return new CursoResponseDto(this.cursoRepository.save(curso));
    }

    @Override
    public void enable(Long id) throws CursoNoExistenteException, EntityDisabledException {
        Curso curso = searchCurso(id);

        if (curso.getActivo()) {
            throw new EntityDisabledException("El curso ya estava habilitado");
        }

        curso.setActivo(true);
    }

    @Override
    public void disable(Long id) throws CursoNoExistenteException, EntityDisabledException {
        Curso curso = searchCurso(id);

        if (!curso.getActivo()) {
            throw new EntityDisabledException("El curso ya estava deshabilitado");
        }

        curso.setActivo(false);
    }

    @Override
    public CursoResponseDto update(CursoDtoUpdate dto, Long id)
            throws CursoNoExistenteException, EntityDisabledException {

        Curso curso = searchCurso(id);
        curso.update(dto);

        return new CursoResponseDto(this.cursoRepository.save(curso));
    }

    /**
     * Metodo sobrecargado privado que busca un curso por su id o por su nombre.
     * 
     * @param id el id del curso.
     * @return la entidad curso.
     * @throws EscuelaNoExistenteException si la escuela no existe.
     * @throws EntityDisabledException     si la escuela esta deshabilitada.
     */
    private Curso searchCurso(Long id) throws CursoNoExistenteException, EntityDisabledException {
        Curso curso = this.cursoRepository.findById(id)
                .orElseThrow(() -> new CursoNoExistenteException("El curso no existe"));

        if (!curso.getEscuela().getActivo()) {
            throw new EntityDisabledException("La escuela de este curso está deshabilitada");
        }

        return curso;
    }

    private Curso searchCurso(String curso) throws CursoNoExistenteException, EntityDisabledException {
        Curso cursoEntity = this.cursoRepository.findByNombre(curso)
                .orElseThrow(() -> new CursoNoExistenteException("El curso no existe"));

        if (!cursoEntity.getEscuela().getActivo()) {
            throw new EntityDisabledException("La escuela de este curso está deshabilitada");
        }

        return cursoEntity;
    }

    /**
     * Metodo privado que busca una escuela por su id.
     * 
     * @param id el id de la escuela.
     * @return la entidad escuela.
     * @throws EscuelaNoExistenteException si la escuela no existe.
     * @throws EntityDisabledException     si la escuela esta deshabilitada.
     */
    private Escuela searchEscuela(Long id) throws EscuelaNoExistenteException, EntityDisabledException {
        Optional<Escuela> escuela = this.escuelaRepository.findById(id);
        escuela.orElseThrow(() -> new EscuelaNoExistenteException("La escuela no existe"));
        Escuela escuelaEntity = escuela.get();

        if (!escuelaEntity.getActivo()) {
            throw new EntityDisabledException("La escuela está deshabilitada");
        }

        return escuelaEntity;
    }

}
