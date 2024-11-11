package com.escuelas.project.escuelas_project.asistencia.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.escuelas.project.escuelas_project.alumno.entities.Alumno;
import com.escuelas.project.escuelas_project.alumno.exceptions.AlumnoNoExistenteException;
import com.escuelas.project.escuelas_project.alumno.repository.AlumnoRepository;
import com.escuelas.project.escuelas_project.asistencia.entities.Asistencia;
import com.escuelas.project.escuelas_project.asistencia.entities.AsistenciaDto;
import com.escuelas.project.escuelas_project.asistencia.entities.AsistenciaResponseDto;
import com.escuelas.project.escuelas_project.asistencia.entities.AsistenciaResponsePorClaseDto;
import com.escuelas.project.escuelas_project.asistencia.entities.AsistenciaStats;
import com.escuelas.project.escuelas_project.asistencia.entities.AsistenciaUpdateDto;
import com.escuelas.project.escuelas_project.asistencia.entities.AsistioEnum;
import com.escuelas.project.escuelas_project.asistencia.exceptions.AsistenciaExistenteException;
import com.escuelas.project.escuelas_project.asistencia.exceptions.AsistenciaNoExistenteException;
import com.escuelas.project.escuelas_project.asistencia.repository.AsistenciaRepository;
import com.escuelas.project.escuelas_project.clase.entities.Clase;
import com.escuelas.project.escuelas_project.clase.exceptions.ClaseNoExistenteException;
import com.escuelas.project.escuelas_project.clase.repository.ClaseRepository;
import com.escuelas.project.escuelas_project.service.exceptions.EntityDisabledException;

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
            throws AlumnoNoExistenteException, EntityDisabledException, ClaseNoExistenteException,
            AsistenciaExistenteException {

        Clase clase = this.searchClase(idClase);

        if (clase.getAsistencias().size() > 0) {
            throw new AsistenciaExistenteException("La clase ya tiene asistencias");
        }

        ArrayList<AsistenciaResponseDto> asistenciaList = new ArrayList<>();

        for (AsistenciaDto asistencia : asistenciaDto) {
            Alumno alumno = this.searchAlumno(asistencia.id());
            Optional<Asistencia> asistenciaOptional = this.asistenciaRepository.findByAlumnoAndClase(alumno, clase);
            asistenciaOptional.ifPresentOrElse(a -> {
                asistenciaOptional.get().update(asistencia);
                asistenciaList.add(new AsistenciaResponseDto(asistenciaOptional.get()));
            }, () -> {
                Asistencia newAsistencia = new Asistencia(alumno, clase, asistencia);
                asistenciaList.add(new AsistenciaResponseDto(this.asistenciaRepository.save(newAsistencia)));
            });

        }
        return asistenciaList;
    }

    @Override
    public AsistenciaResponseDto updateAsistencia(Long id, AsistenciaUpdateDto asistenciaUpdateDto)
            throws AsistenciaNoExistenteException, EntityDisabledException {
        System.out.println(asistenciaUpdateDto);
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

    @Override
    public List<AsistenciaStats> findAllAsistenciaDtoByClaseStats(Long id)
            throws ClaseNoExistenteException, EntityDisabledException {

        List<Asistencia> asistencias = this.findAllAsistenciaByClase(id);
        HashMap<AsistioEnum, AsistenciaStats> asistenciasStats = new HashMap<>();

        asistencias.forEach(a -> asistenciasStats.computeIfAbsent(a.getAsistio(), AsistenciaStats::new).updateTotal());

        return new ArrayList<>(asistenciasStats.values());
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

    /**
     * Busca una Asistencia por su ID y lanza una excepción si no existe.
     *
     * @param id el ID de la Asistencia a buscar
     * @return la Asistencia encontrada
     * @throws AsistenciaNoExistenteException si la Asistencia con el ID dado no
     *                                        existe
     * @throws EntityDisabledException        si el Alumno o el Curso de la
     *                                        Asistencia no están activos
     */
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

    private List<Asistencia> findAllAsistenciaByClase(Long id)
            throws ClaseNoExistenteException, EntityDisabledException {
        Clase clase = this.searchClase(id);
        Optional<List<Asistencia>> asistencias = this.asistenciaRepository.findAllAsistenciaByClase(clase);

        if (!asistencias.isPresent()) {
            throw new ClaseNoExistenteException("La clase no existe");
        }

        return asistencias.get();
    }

}
