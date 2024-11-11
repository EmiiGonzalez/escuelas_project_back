package com.escuelas.project.escuelas_project.asistencia.controllers;

import java.util.ArrayList;
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

import com.escuelas.project.escuelas_project.alumno.exceptions.AlumnoNoExistenteException;
import com.escuelas.project.escuelas_project.asistencia.entities.AsistenciaDto;
import com.escuelas.project.escuelas_project.asistencia.entities.AsistenciaResponseDto;
import com.escuelas.project.escuelas_project.asistencia.entities.AsistenciaResponsePorClaseDto;
import com.escuelas.project.escuelas_project.asistencia.entities.AsistenciaStats;
import com.escuelas.project.escuelas_project.asistencia.entities.AsistenciaUpdateDto;
import com.escuelas.project.escuelas_project.asistencia.exceptions.AsistenciaExistenteException;
import com.escuelas.project.escuelas_project.asistencia.exceptions.AsistenciaNoExistenteException;
import com.escuelas.project.escuelas_project.asistencia.services.AsistenciaService;
import com.escuelas.project.escuelas_project.clase.exceptions.ClaseNoExistenteException;
import com.escuelas.project.escuelas_project.service.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.dtos.response.ResponseMessage;
import com.escuelas.project.escuelas_project.service.utils.PersonalizedMessage;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * 
 * Controlador API REST para la gestión de los objetos {@link Asistencia}.
 * 
 * @author Emilio Gonzalez
 * @version 1.1
 * @since 1.0
 * 
 * @see AsistenciaService
 * @see Asistencia
 * @see AsistenciaDto
 * @see AsistenciaResponseDto
 * @see AsistenciaUpdateDto
 * @see EntityDisabledException
 * @see AlumnoNoExistenteException
 * @see ClaseNoExistenteException
 * @see AsistenciaNoExistenteException
 **/
@RestController
@RequestMapping("/api/v1/asistencias")
@AllArgsConstructor
public class AsistenciaController {

    private final AsistenciaService asistenciaService;
    private PersonalizedMessage personalizedMessage;

    // POST

    /**
     * Guarda una lista de objetos AsistenciaDto asociados a un claseId dado.
     *
     * @param claseId el ID de la clase a la que se asociarán los objetos
     *                AsistenciaDto
     * @param dto     la lista de objetos AsistenciaDto a guardar
     * @return un ResponseEntity que contiene una lista de objetos
     *         AsistenciaResponseDto que representan los objetos AsistenciaDto
     *         guardados
     * @throws AlumnoNoExistenteException si un Alumno con el ID proporcionado no
     *                                    existe
     * @throws EntityDisabledException    si una entidad está deshabilitada
     * @throws ClaseNoExistenteException  si una Clase con el ID proporcionado no
     *                                    existe
     * @throws AsistenciaExistenteException 
     */
    @PostMapping("/save/{claseId}")
    @ResponseBody
    @Transactional
    public ResponseEntity<ArrayList<AsistenciaResponseDto>> save(@PathVariable Long claseId,
            @RequestBody @Valid List<AsistenciaDto> dto)
            throws AlumnoNoExistenteException, EntityDisabledException, ClaseNoExistenteException, AsistenciaExistenteException {
        return ResponseEntity.status(HttpStatus.CREATED).body(asistenciaService.createAsistencia(dto, claseId));
    }

    // GET
    /**
     * Recupera un AsistenciaResponseDto por su ID.
     *
     * @param id el ID del AsistenciaResponseDto a recuperar
     * @return un ResponseEntity que contiene el AsistenciaResponseDto recuperado
     * @throws AsistenciaNoExistenteException si la Asistencia no existe
     * @throws EntityDisabledException        si una entidad está deshabilitada
     */
    @GetMapping("/find/{id}")
    @ResponseBody
    public ResponseEntity<AsistenciaResponseDto> findById(@PathVariable Long id)
            throws AsistenciaNoExistenteException, EntityDisabledException {
        return ResponseEntity.ok(asistenciaService.findByIdAsistencia(id));
    }

    /**
     * Recupera una lista de AsistenciaResponseDto por el ID del Alumno.
     *
     * @param id el ID del Alumno para recuperar AsistenciaResponseDto
     * @return un ResponseEntity que contiene una lista de AsistenciaResponseDto
     *         para el Alumno
     * @throws EntityDisabledException    si una entidad está deshabilitada
     * @throws AlumnoNoExistenteException si el Alumno no existe
     */
    @GetMapping("/find/all/alumno/{id}")
    @ResponseBody
    public ResponseEntity<List<AsistenciaResponseDto>> findAllByAlumno(@PathVariable Long id)
            throws EntityDisabledException, AlumnoNoExistenteException {
        return ResponseEntity.ok(asistenciaService.findAllAsistenciaDtoByAlumno(id));
    }

    /**
     * Recupera una_lista de AsistenciaResponseDto por el ID de la Clase.
     *
     * @param id el ID de la Clase para recuperar AsistenciaResponseDto
     * @return un ResponseEntity que contiene una_lista de AsistenciaResponseDto
     *         para la Clase
     * @throws EntityDisabledException    si una entidad está deshabilitada
     * @throws ClaseNoExistenteException  si la Clase no existe
     */
    @GetMapping("/find/all/clase/stats/{id}")
    @ResponseBody
    public ResponseEntity<List<AsistenciaStats>> findAllByClaseStats(@PathVariable Long id)
            throws EntityDisabledException, ClaseNoExistenteException {
        return ResponseEntity.ok(asistenciaService.findAllAsistenciaDtoByClaseStats(id));
    }

    /**
     * Recupera una lista de AsistenciaResponsePorClaseDto por el ID de la Clase.
     *
     * @param id el ID de la Clase para recuperar AsistenciaResponsePorClaseDto
     * @return un ResponseEntity que contiene una lista de
     *         AsistenciaResponsePorClaseDto para la Clase
     * @throws ClaseNoExistenteException si la Clase no existe
     * @throws EntityDisabledException   si una entidad está deshabilitada
     */
    @GetMapping("/find/all/clase/{id}")
    @ResponseBody
    public ResponseEntity<List<AsistenciaResponsePorClaseDto>> findAllByClase(@PathVariable Long id)
            throws ClaseNoExistenteException, EntityDisabledException {
        return ResponseEntity.ok(asistenciaService.findAllAsistenciaDtoByClase(id));
    }

    // PUT
    /**
     * Actualiza una Asistencia utilizando un AsistenciaUpdateDto y su ID.
     *
     * @param dto el AsistenciaUpdateDto con la información actualizada
     * @param id  el ID de la Asistencia a actualizar
     * @return un ResponseEntity que contiene el AsistenciaResponseDto actualizado
     * @throws AsistenciaNoExistenteException si la Asistencia no existe
     * @throws EntityDisabledException        si una entidad está deshabilitada
     */
    @PutMapping("/update/{id}")
    @ResponseBody
    @Transactional
    public ResponseEntity<AsistenciaResponseDto> update(@RequestBody @Valid AsistenciaUpdateDto dto, @PathVariable Long id)
            throws AsistenciaNoExistenteException, EntityDisabledException {
        return ResponseEntity.status(HttpStatus.OK).body(asistenciaService.updateAsistencia(id, dto));
    }

    // DELETE
    /**
     * Elimina una Asistencia por su ID.
     *
     * @param id el ID de la Asistencia a eliminar
     * @return un ResponseEntity que contiene un mensaje de respuesta
     * @throws AsistenciaNoExistenteException si la Asistencia no existe
     * @throws EntityDisabledException        si una entidad está deshabilitada
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    @Transactional
    public ResponseEntity<ResponseMessage> delete(@PathVariable Long id)
            throws AsistenciaNoExistenteException, EntityDisabledException {
        asistenciaService.deleteAsistencia(id);
        ResponseMessage responseMessage = new ResponseMessage(
                personalizedMessage.asistenciaDeleted().replace("$", id.toString()), 0);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

}
