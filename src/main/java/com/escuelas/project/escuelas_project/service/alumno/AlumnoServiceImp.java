package com.escuelas.project.escuelas_project.service.alumno;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.escuelas.project.escuelas_project.persistence.entity.Alumno;
import com.escuelas.project.escuelas_project.persistence.entity.Curso;
import com.escuelas.project.escuelas_project.persistence.repository.AlumnoRepository;
import com.escuelas.project.escuelas_project.persistence.repository.CursoRepository;
import com.escuelas.project.escuelas_project.service.models.dtos.alumno.AlumnoDto;
import com.escuelas.project.escuelas_project.service.models.dtos.alumno.AlumnoDtoUpdate;
import com.escuelas.project.escuelas_project.service.models.dtos.alumno.AlumnoResponseDto;
import com.escuelas.project.escuelas_project.service.models.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.exceptions.alumnoExceptions.AlumnoNoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.cursoExceptions.CursoNoExistenteException;

import lombok.AllArgsConstructor;

/**
 * Esta clase implementa la interfaz {@link AlumnoService} y proporciona las
 * operaciones de servicio para las clases.
 *
 * @author Emiliano Gonzalez
 * @version 1.0
 * @since 1.0
 */

@Service
@AllArgsConstructor
public class AlumnoServiceImp implements AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final CursoRepository cursoRepository;

   /**
     * Crea un nuevo alumno asociado a un curso
     *
     * @param  dto   AlumnoDto con datos del nuevo alumno
     * @param  id   Long con el id del curso
     * @return      AlumnoResponseDto con los datos del nuevo alumno
     * @throws CursoNoExistenteException si el curso no existe
     * @throws EntityDisabledException si el curso o el alumno están deshabilitados
     */
    @Override
    public AlumnoResponseDto create(AlumnoDto dto, Long id)
            throws CursoNoExistenteException, EntityDisabledException {
        Curso curso = searchCurso(id);
        return new AlumnoResponseDto(this.alumnoRepository.save(new Alumno(dto, curso)));
    }

    /**
     * Actualiza los datos de un alumno
     *
     * @param  dto   AlumnoDtoUpdate con los datos a actualizar
     * @param  id   Long con el id del alumno
     * @return      AlumnoResponseDto con los datos actualizados
     * @throws AlumnoNoExistenteException si el alumno no existe
     * @throws EntityDisabledException si el alumno o el curso están deshabilitados
     */
    @Override
    public AlumnoResponseDto update(AlumnoDtoUpdate dto, Long id)
            throws AlumnoNoExistenteException, EntityDisabledException {
        Alumno alumno = searchAlumno(id);
        alumno.update(dto);
        return new AlumnoResponseDto(this.alumnoRepository.save(alumno));
    }

    /**
     * Deshabilita un alumno
     *
     * @param  id   Long con el id del alumno
     * @throws AlumnoNoExistenteException si el alumno no existe
     * @throws EntityDisabledException si el alumno o el curso están deshabilitados
     */
    @Override
    public void disable(Long id) throws AlumnoNoExistenteException, EntityDisabledException {
        String msg = "El alumno ya estaba deshabilitado";
        Alumno alumno = changeStatus(id, msg);
        alumno.setActivo(false);
    }

    /**
     * Habilita un alumno
     *
     * @param  id   Long con el id del alumno
     * @throws AlumnoNoExistenteException si el alumno no existe
     * @throws EntityDisabledException si el alumno o el curso están deshabilitados
     */
    @Override
    public void enable(Long id) throws AlumnoNoExistenteException, EntityDisabledException {
        String msg = "El alumno ya estaba habilitado";
        Alumno alumno = changeStatus(id, msg);
        alumno.setActivo(true);
    }

    /**
     * Busca un alumno por su id
     *
     * @param  id   Long con el id del alumno
     * @return      AlumnoResponseDto con los datos del alumno
     * @throws AlumnoNoExistenteException si el alumno no existe
     * @throws EntityDisabledException si el alumno o el curso están deshabilitados
     */
    @Override
    public AlumnoResponseDto findById(Long id)
            throws AlumnoNoExistenteException, EntityDisabledException {
        return new AlumnoResponseDto(searchAlumno(id));
    }

    /**
     * Busca todos los alumnos activos asociados a un curso
     *
     * @param  id   Long con el id del curso
     * @return      List con AlumnoResponseDto de los alumnos activos
     * @throws EntityDisabledException si el curso está deshabilitado
     * @throws CursoNoExistenteException si el curso no existe
     */
    @Override
    public List<AlumnoResponseDto> findAllActiveCurso(Long id)
            throws EntityDisabledException, CursoNoExistenteException {
        Curso curso = searchCurso(id);
        return this.alumnoRepository.findAllActiveByCurso(curso);
    }

    /**
     * Busca todos los alumnos activos
     *
     * @return List con AlumnoResponseDto de los alumnos activos
     */
    @Override
    public List<AlumnoResponseDto> findAllActive() {
        return this.alumnoRepository.findAllActive();
    }

    /**
     * Busca todos los alumnos
     *
     * @return List con AlumnoResponseDto de los alumnos
     */
    @Override
    public List<AlumnoResponseDto> findAll() {
        return this.alumnoRepository.findAllDto();
    };

    /**
     * Busca un Alumno por ID y asegura su disponibilidad.
     *
     * @param id el ID del Alumno a buscar
     * @return el Alumno si se encuentra y está activo
     * @throws AlumnoNoExistenteException si no existe un Alumno con el ID
     *                                   proporcionado
     * @throws EntityDisabledException   si el Alumno con el ID proporcionado no está
     *                                   habilitado
     */

    private Alumno searchAlumno(Long id) throws AlumnoNoExistenteException, EntityDisabledException {
        Optional<Alumno> alumnoOptional = this.alumnoRepository.findById(id);

        alumnoOptional.orElseThrow(() -> new AlumnoNoExistenteException("El alumno no existe"));

        Alumno alumno = alumnoOptional.get();

        if (!alumno.getActivo() || !alumno.getCurso().getActivo()) {
            String msg = !alumno.getActivo() ? "El alumno esta deshabilitado"
                    : "El curso del alumno no esta habilitado";
            throw new EntityDisabledException(msg);
        }

        return alumno;
    }

    /**
     * Busca un Alumno por ID, asegura su disponibilidad y realiza un delete logico o habilita.
     *
     * @param id el ID del Alumno a buscar
     * @return el Alumno si se encuentra y está activo
     * @throws AlumnoNoExistenteException si no existe un curso con el ID
     *                                   proporcionado
     * @throws EntityDisabledException   si el Curso del Alumno no está habilitado
     */

    private Alumno changeStatus(Long id, String messaje) throws AlumnoNoExistenteException, EntityDisabledException {
        Optional<Alumno> alumnoOptional = this.alumnoRepository.findById(id);
        alumnoOptional.orElseThrow(() -> new AlumnoNoExistenteException("El alumno no existe"));
        Alumno alumno = alumnoOptional.get();
        if (alumno.getActivo() || !alumno.getCurso().getActivo()) {

            String msg = alumno.getActivo() == true ? messaje
                    : "El curso del alumno no esta habilitado";
            throw new EntityDisabledException(msg);
        }
        return alumno;
    }

    /**
     * Busca un Curso por su ID.
     *
     * @param  id  el ID del Curso a buscar
     * @return     el Curso si se encuentra y está activo
     * @throws EntityDisabledException  si el Curso no está activo
     * @throws CursoNoExistenteException  si no existe ningún Curso con el ID dado
     */
    private Curso searchCurso(Long id) throws EntityDisabledException, CursoNoExistenteException {
        Optional<Curso> cursoOptional = this.cursoRepository.findById(id);
        cursoOptional.orElseThrow(() -> new CursoNoExistenteException("El curso no existe"));
        if (!cursoOptional.get().getActivo()) {
            throw new EntityDisabledException("El curso no esta habilitado");
        }
        return cursoOptional.get();
    }
}


