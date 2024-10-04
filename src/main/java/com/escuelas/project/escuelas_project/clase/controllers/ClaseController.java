package com.escuelas.project.escuelas_project.clase.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.escuelas.project.escuelas_project.clase.entities.Clase;
import com.escuelas.project.escuelas_project.clase.entities.ClaseCountResponseDto;
import com.escuelas.project.escuelas_project.clase.entities.ClaseDto;
import com.escuelas.project.escuelas_project.clase.entities.ClaseResponseDto;
import com.escuelas.project.escuelas_project.clase.entities.ClaseUpdateDto;
import com.escuelas.project.escuelas_project.clase.exceptions.ClaseNoExistenteException;
import com.escuelas.project.escuelas_project.clase.services.ClaseService;
import com.escuelas.project.escuelas_project.curso.exceptions.CursoNoExistenteException;
import com.escuelas.project.escuelas_project.service.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.dtos.response.ResponseMessage;
import com.escuelas.project.escuelas_project.service.utils.PersonalizedMessage;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

/**
 * Controlador API REST para la gestión de los objetos {@link Clase}.
 * 
 * @author Emiliano Gonzalez
 * 
 * @version 1.0
 * @since 1.0
 * 
 * @see ClaseService
 * @see Clase
 * @see ClaseDto
 * @see ClaseResponseDto
 * @see ClaseUpdateDto
 * @see EntityDisabledException
 * @see ClaseNoExistenteException
 * @see CursoNoExistenteException
 * 
 **/
@RestController
@RequestMapping("/api/v1/clases")
@AllArgsConstructor
public class ClaseController {

    private final ClaseService claseService;
    private PersonalizedMessage personalizedMessage;

    // ----------------------- GET START ----------------------------------

    /**
     * Recupera todas las clases basadas en el ID del curso proporcionado.
     *
     * @param id El ID del curso utilizado para encontrar todas las clases.
     * @return ResponseEntity conteniendo una lista de objetos ClaseResponseDto.
     * @throws EntityDisabledException   Si el curso no existe o esta deshabilitado.
     * @throws CursoNoExistenteException Si el curso no existe.
     */
    @GetMapping(value = "/find/all/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<Page<ClaseResponseDto>> findAll(@PathVariable Long id,
            @PageableDefault(size = 5, page = 0) Pageable pageable,
            @RequestParam(name = "sort", defaultValue = "fecha_de_clase") String sortBy,
            @RequestParam(name = "direction", defaultValue = "DESC") String direction)
            throws CursoNoExistenteException, EntityDisabledException {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return ResponseEntity.status(HttpStatus.OK).body(claseService.findAll(id, pageable));
    }

    /**
     * Encuentra una clase por su ID.
     *
     * @param id El ID de la clase a encontrar.
     * @return ResponseEntity que contiene un objeto ClaseResponseDto.
     * @throws ClaseNoExistenteException Excepción lanzada si la clase no existe.
     * @throws EntityDisabledException   Excepción lanzada si la entidad está
     *                                   deshabilitada.
     */
    @GetMapping(value = "find/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<ClaseResponseDto> findById(@PathVariable Long id)
            throws ClaseNoExistenteException, EntityDisabledException {
        return ResponseEntity.status(HttpStatus.OK).body(claseService.findById(id));
    }

    /**
     * Cuenta el número de clases asociadas a un curso dado.
     *
     * @param id El ID del curso.
     * @return ResponseEntity que contiene un objeto ClaseCountResponseDto.
     * @throws CursoNoExistenteException Excepción lanzada si el curso no existe.
     * @throws EntityDisabledException   Excepción lanzada si la entidad está
     *                                   deshabilitada.
     */
    @GetMapping(value = "/count/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<ClaseCountResponseDto> count(@PathVariable Long id)
            throws CursoNoExistenteException, EntityDisabledException {
        return ResponseEntity.status(HttpStatus.OK).body(claseService.count(id));
    }

    // ----------------------- GET END ----------------------------------

    // ----------------------- POST START ----------------------------------

    /**
     * Guarda una nueva clase asociada a un curso.
     *
     * @param id  El ID del curso al que se asociará la clase.
     * @param dto Objeto ClaseDto que contiene la información de la clase a guardar.
     * @return ResponseEntity que contiene un objeto ClaseResponseDto con la
     *         información de la clase guardada.
     * @throws CursoNoExistenteException Excepción lanzada si el curso no existe.
     * @throws EntityDisabledException   Excepción lanzada si la entidad está
     *                                   deshabilitada.
     */
    @PostMapping(value = "/save/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<ClaseResponseDto> save(@PathVariable Long id, @RequestBody ClaseDto dto)
            throws CursoNoExistenteException, EntityDisabledException {
        return ResponseEntity.status(HttpStatus.CREATED).body(claseService.save(dto, id));
    }

    // ----------------------- POST END ----------------------------------

    // ----------------------- PUT START ----------------------------------

    /**
     * Actualiza la información de una clase existente.
     *
     * @param id  El ID de la clase a actualizar.
     * @param dto Objeto ClaseUpdateDto con la nueva información de la clase.
     * @return ResponseEntity que contiene un objeto ClaseResponseDto con la
     *         información actualizada de la clase.
     * @throws ClaseNoExistenteException Excepción lanzada si la clase no existe.
     * @throws EntityDisabledException   Excepción lanzada si la entidad está
     *                                   deshabilitada.
     */
    @PutMapping(value = "/update/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<ClaseResponseDto> update(@PathVariable Long id, @RequestBody ClaseUpdateDto dto)
            throws ClaseNoExistenteException, EntityDisabledException {
        return ResponseEntity.status(HttpStatus.OK).body(claseService.update(dto, id));
    }

    // ----------------------- PUT END ----------------------------------

    // ----------------------- DELETE START ----------------------------------

    /**
     * Elimina logicamente una clase existente.
     *
     * @param id El ID de la clase a eliminar.
     * @return ResponseEntity que contiene un objeto ResponseMessage con un mensaje
     *         personalizado.
     * @throws ClaseNoExistenteException Excepción lanzada si la clase no existe.
     * @throws EntityDisabledException   Excepción lanzada si la entidad está
     *                                   deshabilitada.
     */
    @DeleteMapping(value = "/delete/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<ResponseMessage> delete(@PathVariable Long id)
            throws ClaseNoExistenteException, EntityDisabledException {
        claseService.deleteById(id);
        ResponseMessage responseMessage = new ResponseMessage(
                personalizedMessage.claseDeleted().replace("$", id.toString()), 0);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    // ----------------------- DELETE END ----------------------------------

}
