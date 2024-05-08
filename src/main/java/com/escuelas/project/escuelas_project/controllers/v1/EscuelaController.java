package com.escuelas.project.escuelas_project.controllers.v1;

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

import com.escuelas.project.escuelas_project.service.escuela.EscuelaService;
import com.escuelas.project.escuelas_project.service.models.dtos.escuela.EscuelaDto;
import com.escuelas.project.escuelas_project.service.models.dtos.escuela.EscuelaDtoUpdate;
import com.escuelas.project.escuelas_project.service.models.dtos.escuela.EscuelaResponseDto;
import com.escuelas.project.escuelas_project.service.models.dtos.response.ResponseMessage;
import com.escuelas.project.escuelas_project.service.models.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.exceptions.escuelaExceptions.EscuelaExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.escuelaExceptions.EscuelaNoExistenteException;
import com.escuelas.project.escuelas_project.service.utils.PersonalizedMessage;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Controlador API REST para la gestión de los objetos {@link Escuela}.
 * 
 * @author Emiliano Gonzalez
 * 
 * @version 1.0
 * @since 1.0
 * 
 * @see EscuelaService
 * @see Escuela
 * @see EscuelaDto
 * @see EscuelaDtoUpdate
 * @see EscuelaResponseDto
 * @see EntityDisabledException
 * @see EscuelaNoExistenteException
 * @see EscuelaExistenteException
 * 
 **/

@RestController
@RequestMapping("/api/v1/escuelas")
@AllArgsConstructor
public class EscuelaController {
    private final EscuelaService escuelaService;
    private PersonalizedMessage personalizedMessage;

    // ----------------------- POST START ----------------------------------
    /**
     * Guarda una nueva Escuela.
     *
     * Este método maneja una solicitud HTTP POST para crear una nueva Escuela.
     * La Escuela se proporciona en el cuerpo de la solicitud en formato JSON.
     *
     * @param escuela La Escuela a guardar.
     * @return Una respuesta HTTP 201 (Creado) con la Escuela guardada en el cuerpo
     *         de la respuesta.
     * @throws EscuelaExistenteException Si ya existe una Escuela con el mismo
     *                                   nombre.
     */
    @PostMapping(value = "/save", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<EscuelaResponseDto> save(@RequestBody @Valid EscuelaDto escuela)
            throws EscuelaExistenteException {
        System.out.println(escuela);
        return ResponseEntity.status(HttpStatus.CREATED).body(escuelaService.save(escuela));
    }
    // ----------------------- POST END ----------------------------------

    // ----------------------- GET START ----------------------------------

    /**
     * Busca una Escuela por su ID.
     *
     * Este método maneja una solicitud HTTP GET para buscar una Escuela por su ID.
     * El ID de la Escuela se proporciona en la URL de la solicitud.
     *
     * @param id El ID de la Escuela a buscar.
     * @return Una respuesta HTTP 200 (OK) con la Escuela encontrada en el cuerpo de
     *         la respuesta.
     * @throws EscuelaNoExistenteException Si la Escuela con el ID especificado no
     *                                     existe.
     * @throws EntityDisabledException     Si la Escuela con el ID especificado está
     *                                     deshabilitada.
     */
    @GetMapping(value = "/find/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<EscuelaResponseDto> findById(@PathVariable Long id)
            throws EscuelaNoExistenteException, EntityDisabledException {
        return ResponseEntity.ok(escuelaService.findById(id));
    }

    /**
     * Busca todas las Escuelas activas.
     *
     * Este método maneja una solicitud HTTP GET para buscar todas las Escuelas
     * activas.
     *
     * @return Una respuesta HTTP 200 (OK) con la lista de Escuelas activas en el
     *         cuerpo de la respuesta.
     */
    @GetMapping(value = "/find/all/active", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<EscuelaResponseDto>> findAllActive() {
        return ResponseEntity.status(HttpStatus.OK).body(escuelaService.findAllActive());
    }

    /**
     * Busca todas las Escuelas.
     *
     * Este método maneja una solicitud HTTP GET para buscar todas las Escuelas.
     *
     * @return Una respuesta HTTP 200 (OK) con la lista de Escuelas en el cuerpo de
     *         la respuesta.
     */
    @GetMapping(value = "/find/all", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<EscuelaResponseDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(escuelaService.findAll());
    }

    // -------------------------- GET END ------------------------------------------

    // ------------------------- DELETE START --------------------------------------

    /**
     * Deshabilita una Escuela por su ID.
     *
     * Este método maneja una solicitud HTTP DELETE para deshabilitar una Escuela
     * por su ID.
     * El ID de la Escuela se proporciona en la URL de la solicitud.
     *
     * @param id El ID de la Escuela a deshabilitar.
     * @return Una respuesta HTTP 200 (OK) con un mensaje de respuesta indicando que
     *         la Escuela fue deshabilitada correctamente.
     * @throws EscuelaNoExistenteException Si la Escuela con el ID especificado no
     *                                     existe.
     * @throws EntityDisabledException     Si la Escuela con el ID especificado ya
     *                                     está deshabilitada.
     */
    @DeleteMapping(value = "/disable/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<ResponseMessage> disable(@PathVariable Long id)
            throws EscuelaNoExistenteException, EntityDisabledException {
        escuelaService.disable(id);
        ResponseMessage responseMessage = new ResponseMessage(
                personalizedMessage.escuelaDeleted().replace("$", id.toString()), 0);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    // ------------------------- DELETE EDN ---------------------------------------

    // ------------------------- PUT START ----------------------------------------

    /**
     * Actualiza una Escuela por su ID.
     *
     * Este método maneja una solicitud HTTP PUT para actualizar una Escuela por su
     * ID.
     * La nueva información de la Escuela se proporciona en el cuerpo de la
     * solicitud en formato JSON.
     *
     * @param escuela La nueva información de la Escuela.
     * @param id      El ID de la Escuela a actualizar.
     * @return Una respuesta HTTP 200 (OK) con la Escuela actualizada en el cuerpo
     *         de la respuesta.
     * @throws EscuelaNoExistenteException Si la Escuela con el ID especificado no
     *                                     existe.
     * @throws EntityDisabledException     Si la Escuela con el ID especificado está
     *                                     deshabilitada.
     */
    @PutMapping(value = "/update/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<EscuelaResponseDto> update(@RequestBody EscuelaDtoUpdate escuela, @PathVariable Long id)
            throws EscuelaNoExistenteException, EntityDisabledException {
        return ResponseEntity.status(HttpStatus.OK).body(escuelaService.update(escuela, id));
    }

    /**
     * Habilita una Escuela por su ID.
     *
     * Este método maneja una solicitud HTTP PUT para habilitar una Escuela por su
     * ID.
     *
     * @param id El ID de la Escuela a habilitar.
     * @return Una respuesta HTTP 200 (OK) con un mensaje de respuesta indicando que
     *         la Escuela fue habilitada correctamente.
     * @throws EscuelaNoExistenteException Si la Escuela con el ID especificado no
     *                                     existe.
     * @throws EntityDisabledException     Si la Escuela con el ID especificado ya
     *                                     está habilitada.
     */
    @PutMapping(value = "/enable/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<ResponseMessage> enable(@PathVariable Long id)
            throws EscuelaNoExistenteException, EntityDisabledException {
        escuelaService.enable(id);
        ResponseMessage responseMessage = new ResponseMessage(
                personalizedMessage.escuelaEnabled().replace("$", id.toString()), 0);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    // ------------------------- PUT END ------------------------------------------
}
