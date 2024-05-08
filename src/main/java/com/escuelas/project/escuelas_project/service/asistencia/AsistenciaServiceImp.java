package com.escuelas.project.escuelas_project.service.asistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.escuelas.project.escuelas_project.persistence.entity.Alumno;
import com.escuelas.project.escuelas_project.persistence.entity.Asistencia;
import com.escuelas.project.escuelas_project.persistence.entity.Clase;
import com.escuelas.project.escuelas_project.persistence.repository.AlumnoRepository;
import com.escuelas.project.escuelas_project.persistence.repository.AsistenciaRepository;
import com.escuelas.project.escuelas_project.persistence.repository.ClaseRepository;
import com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaDto;
import com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaResponseDto;
import com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaResponsePorClaseDto;
import com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaUpdateDto;
import com.escuelas.project.escuelas_project.service.models.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.exceptions.alumnoExceptions.AlumnoNoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.asistenciaExceptions.AsistenciaNoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.clasesExceptions.ClaseNoExistenteException;
import lombok.AllArgsConstructor;

/**
 * Esta clase implementa la interfaz {@link AsistenciaService} y proporciona las
 * operaciones de servicio para las clases.
 *
 * @author Emiliano Gonzalez
 * @version 1.0
 * @since 1.0
 */
@Service
@AllArgsConstructor
public class AsistenciaServiceImp implements AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    private final ClaseRepository claseRepository;
    private final AlumnoRepository alumnoRepository;

    @Override
    public ArrayList<AsistenciaResponseDto> createAsistencia(List<AsistenciaDto> asistenciaDto, Long idClase)
            throws AlumnoNoExistenteException, EntityDisabledException, ClaseNoExistenteException {

        Clase clase = this.searchClase(idClase);

        ArrayList<AsistenciaResponseDto> asistenciaList = new ArrayList<>();

        for (AsistenciaDto asistencia : asistenciaDto) {

            Alumno alumno = this.searchAlumno(asistencia.id());
            Asistencia asistenciaEntity = new Asistencia(alumno, clase, asistencia);

            this.asistenciaRepository.save(asistenciaEntity);
            asistenciaList.add(new AsistenciaResponseDto(asistenciaEntity));
        }

        return asistenciaList;
    }

    @Override
    public AsistenciaResponseDto updateAsistencia(Long id, AsistenciaUpdateDto asistenciaUpdateDto)
            throws AsistenciaNoExistenteException, EntityDisabledException {
        Asistencia asistencia = this.searchAsistencia(id);
        asistencia.update(asistenciaUpdateDto);

        return new AsistenciaResponseDto(this.asistenciaRepository.save(asistencia));
    }

    @Override
    public void deleteAsistencia(Long id) throws AsistenciaNoExistenteException, EntityDisabledException {
        this.asistenciaRepository.delete(this.searchAsistencia(id));
    }

    @Override
    public AsistenciaResponseDto findByIdAsistencia(Long id)
            throws AsistenciaNoExistenteException, EntityDisabledException {
        return new AsistenciaResponseDto(this.searchAsistencia(id));
    }

    @Override
    public List<AsistenciaResponseDto> findAllAsistenciaDtoByAlumno(Long id)
            throws AlumnoNoExistenteException, EntityDisabledException {
        Alumno alumno = this.searchAlumno(id);
        return this.asistenciaRepository.findAllAsistenciaDtoByAlumno(alumno);
    }

    @Override
    public List<AsistenciaResponsePorClaseDto> findAllAsistenciaDtoByClase(Long id)
            throws ClaseNoExistenteException, EntityDisabledException {
        Clase clase = this.searchClase(id);
        return this.asistenciaRepository.findAllAsistenciaDtoByClase(clase);
    }

    /**
     * Busca un Alumno por ID y asegura su disponibilidad.
     *
     * @param id el ID del Alumno a buscar
     * @return el Alumno si se encuentra y está activo
     * @throws AlumnoNoExistenteException si no existe un Alumno con el ID
     *                                    proporcionado
     * @throws EntityDisabledException    si el Alumno con el ID proporcionado no
     *                                    está
     *                                    habilitado
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

    private Asistencia searchAsistencia(Long id) throws AsistenciaNoExistenteException, EntityDisabledException {
        Optional<Asistencia> asistenciaOptional = this.asistenciaRepository.findById(id);

        asistenciaOptional.orElseThrow(() -> new AsistenciaNoExistenteException("La asistencia no existe"));

        if (!asistenciaOptional.get().getAlumno().getActivo()) {
            throw new EntityDisabledException("El alumno de la asistencia no esta habilitado");
        }

        if (!asistenciaOptional.get().getClase().getCurso().getActivo()) {
            throw new EntityDisabledException("El curso de la clase no esta habilitado");
        }

        return asistenciaOptional.get();
    }
}
