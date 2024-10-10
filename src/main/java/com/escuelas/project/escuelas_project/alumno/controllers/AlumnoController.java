package com.escuelas.project.escuelas_project.alumno.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.escuelas.project.escuelas_project.alumno.entities.Alumno;
import com.escuelas.project.escuelas_project.alumno.entities.AlumnoDto;
import com.escuelas.project.escuelas_project.alumno.entities.AlumnoDtoUpdate;
import com.escuelas.project.escuelas_project.alumno.entities.AlumnoResponseDto;
import com.escuelas.project.escuelas_project.alumno.entities.AlumnoResponseDtoWithAsistencia;
import com.escuelas.project.escuelas_project.alumno.exceptions.AlumnoNoExistenteException;
import com.escuelas.project.escuelas_project.alumno.services.AlumnoService;
import com.escuelas.project.escuelas_project.curso.exceptions.CursoNoExistenteException;
import com.escuelas.project.escuelas_project.service.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.dtos.response.ResponseMessage;
import com.escuelas.project.escuelas_project.service.utils.PersonalizedMessage;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Controlador API REST para la gestión de los objetos {@link Alumno}.
 * 
 * @author Emiliano Gonzalez
 * @version 1.1
 * @since 1.0
 * 
 * @see AlumnoService
 * @see Alumno
 * @see AlumnoDto
 * @see AlumnoDtoUpdate
 * @see AlumnoResponseDto
 * @see EntityDisabledException
 * @see AlumnoNoExistenteException
 * @see CursoNoExistenteException
 * 
 */

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/alumnos")
public class AlumnoController {

    private final AlumnoService alumnoService;
    private PersonalizedMessage personalizedMessage;

    //----------------------- GET START ----------------------------------

    /**
     * Retorna una lista de objetos {@link AlumnoResponseDto} que representan
     * a todos los alumnos en el sistema. Este método responde a una solicitud
     * GET en la ruta "/api/v1/alumnos/findAll" y espera una cabecera
     * "Accept=application/json".
     *
     * @return Una respuesta con estatus HTTP 200 que contiene una lista de
     *         objetos {@link AlumnoResponseDto}. La lista puede estar vacía si no
     *         hay
     *         alumnos en el sistema.
     */
    @GetMapping(value = "/find/all", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<AlumnoResponseDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(alumnoService.findAll());
    }

    /**
     * Retorna una lista de objetos {@link AlumnoResponseDto} que representan
     * a todos los alumnos activos en el sistema. Este método responde a una
     * solicitud
     * GET en la ruta "/api/v1/alumnos/find/all/active" y espera una cabecera
     * "Accept=application/json".
     *
     * @return Una respuesta con estatus HTTP 200 que contiene una lista de
     *         objetos {@link AlumnoResponseDto}. La lista puede estar vacía si no
     *         hay
     *         alumnos activos en el sistema.
     */
    @GetMapping(value = "/find/all/active", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<AlumnoResponseDto>> findAllActive() {
        return ResponseEntity.status(HttpStatus.OK).body(alumnoService.findAllActive());
    }

    /**
     * Recupera una lista de estudiantes activos para un curso dado.
     *
     * @param id el ID del curso
     * @return un ResponseEntity que contiene una lista de DTO de estudiantes
     *         activos del curso dado
     * @throws EntityDisabledException   si la entidad está deshabilitada
     * @throws CursoNoExistenteException si el curso no existe
     */
    @GetMapping(value = "/find/all/active/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<AlumnoResponseDtoWithAsistencia>> findAllActiveCurso(@PathVariable Long id)
            throws EntityDisabledException, CursoNoExistenteException {
        return ResponseEntity.status(HttpStatus.OK).body(alumnoService.findAllActiveCurso(id));
    }

    /**
     * Recupera un objeto AlumnoResponseDto por su ID.
     *
     * @param id el ID del Alumno
     * @return un ResponseEntity que contiene el objeto AlumnoResponseDto
     * @throws EntityDisabledException    si la entidad está deshabilitada
     * @throws AlumnoNoExistenteException si el Alumno no existe
     */
    @GetMapping(value = "find/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<AlumnoResponseDto> findById(@PathVariable Long id)
            throws EntityDisabledException, AlumnoNoExistenteException {
        return ResponseEntity.status(HttpStatus.OK).body(alumnoService.findById(id));
    }

    //----------------------- GET END ----------------------------------

    //----------------------- POST START -------------------------------

    /**
     * Guarda un objeto Alumno con el ID y DTO proporcionados.
     *
     * @param id  el ID del curso al que se asociara el Alumno a guardar
     * @param dto el DTO que contiene los datos del objeto Alumno
     * @return el ResponseEntity que contiene el objeto AlumnoResponseDto guardado
     */
    @PostMapping(value = "/save/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<AlumnoResponseDto> save(@PathVariable Long id, @RequestBody @Valid AlumnoDto dto)
            throws CursoNoExistenteException, EntityDisabledException {
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.create(dto, id));
    }

    //----------------------- POST END -------------------------------

    //----------------------- PUT START -------------------------------

    /**
     * Actualiza un Alumno con el ID y DTO proporcionados.
     *
     * @param dto el AlumnoDto contiene los datos a actualizar
     * @param id  el ID del Alumno a actualizar
     * @return la ResponseEntity que contiene el AlumnoResponseDto actualizado
     * @throws AlumnoNoExistenteException si el Alumno no existe
     * @throws EntityDisabledException    si la entidad está deshabilitada
     */
    @PutMapping(value = "/update/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<AlumnoResponseDto> update(@RequestBody AlumnoDtoUpdate dto, @PathVariable Long id)
            throws AlumnoNoExistenteException, EntityDisabledException {
        return ResponseEntity.status(HttpStatus.OK).body(alumnoService.update(dto, id));
    }

    /**
     * Habilita un Alumno con el ID proporcionado.
     *
     * @param id el ID del Alumno a habilitar
     * @return un ResponseEntity que indica el estado de la operación
     * @throws AlumnoNoExistenteException si el Alumno no existe
     * @throws EntityDisabledException    si la entidad está deshabilitada
     */
    @PutMapping(value = "/enable/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<ResponseMessage> enable(@PathVariable Long id) throws AlumnoNoExistenteException, EntityDisabledException {
        alumnoService.enable(id);
        ResponseMessage responseMessage = new ResponseMessage(personalizedMessage.alumnoEnabled().replace("$", id.toString()), 0);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    //----------------------- PUT END -------------------------------

    //----------------------- DELETE START -------------------------------

    /**
     * Realiza una eliminación lógica de un Alumno con el ID especificado.
     *
     * @param id el ID del Alumno que se va a eliminar
     * @return un ResponseEntity con estado OK si el Alumno se eliminó correctamente
     * @throws AlumnoNoExistenteException si el Alumno con el ID especificado no
     *                                    existe
     * @throws EntityDisabledException    si el Alumno con el ID especificado ya
     *                                    está deshabilitado
     */
    @DeleteMapping(value = "/delete/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<ResponseMessage> delete(@PathVariable Long id) throws AlumnoNoExistenteException, EntityDisabledException {
        alumnoService.disable(id);
        ResponseMessage responseMessage = new ResponseMessage(personalizedMessage.alumnoDeleted().replace("$", id.toString()), 0);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    //----------------------- DELETE END -------------------------------
}