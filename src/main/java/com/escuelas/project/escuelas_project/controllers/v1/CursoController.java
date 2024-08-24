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

import com.escuelas.project.escuelas_project.service.curso.CursoService;
import com.escuelas.project.escuelas_project.service.models.dtos.curso.CursoDto;
import com.escuelas.project.escuelas_project.service.models.dtos.curso.CursoDtoUpdate;
import com.escuelas.project.escuelas_project.service.models.dtos.curso.CursoResponseDto;
import com.escuelas.project.escuelas_project.service.models.dtos.response.ResponseMessage;
import com.escuelas.project.escuelas_project.service.models.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.exceptions.cursoExceptions.CursoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.cursoExceptions.CursoNoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.escuelaExceptions.EscuelaNoExistenteException;
import com.escuelas.project.escuelas_project.service.utils.PersonalizedMessage;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * 
 * Controlador API REST para la gestión de los objetos {@link Curso}.
 * 
 * @author Emiliano Gonzalez
 * 
 * @version 1.0
 * @since 1.0
 * 
 * @see CursoService
 * @see Curso
 * @see CursoDto
 * @see CursoDtoUpdate
 * @see EntityDisabledException
 * @see EscuelaNoExistenteException
 * @see CursoNoExistenteException
 * @see CursoExistenteException
 * 
 **/

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cursos")
public class CursoController {

    private final CursoService cursoService;
    private PersonalizedMessage personalizedMessage;

    // ----------------------- GET START ----------------------------------

    /**
     * Busca todos los Cursos activos en un año específico para una Escuela.
     *
     * Este método maneja una solicitud HTTP GET para buscar todos los Cursos
     * activos en un año específico para una Escuela.
     * El año y el ID de la Escuela se proporcionan en la URL de la solicitud.
     *
     * @param year El año en el que buscar los Cursos activos.
     * @param id   El ID de la Escuela para la cual buscar los Cursos activos.
     * @return Una respuesta HTTP 200 (OK) con la lista de Cursos activos en el año
     *         y para la Escuela especificados.
     * @throws EntityDisabledException     Si la entidad está deshabilitada.
     * @throws EscuelaNoExistenteException Si la Escuela con el ID especificado no
     *                                     existe.
     */
    @GetMapping(value = "/find/all/{id}/{year}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<CursoResponseDto>> findAllActive(@PathVariable Integer year, @PathVariable Long id)
            throws EntityDisabledException, EscuelaNoExistenteException {
        return ResponseEntity.status(HttpStatus.OK).body(cursoService.findAllActiveDto(year, id));
    }


    // ----------------------- GET END ----------------------------------

    // ----------------------- POST START ----------------------------------

    /**
     * Guarda un nuevo Curso para una Escuela.
     *
     * Este método maneja una solicitud HTTP POST para guardar un nuevo Curso para
     * una Escuela.
     * El ID de la Escuela se proporciona en la URL de la solicitud, y la
     * información del Curso se proporciona en el cuerpo de la solicitud en formato
     * JSON.
     *
     * @param id  El ID de la Escuela para la cual guardar el Curso.
     * @param dto La información del Curso a guardar.
     * @return Una respuesta HTTP 201 (CREATED) con el Curso guardado en el cuerpo
     *         de la respuesta.
     * @throws CursoNoExistenteException   Si el Curso con el nombre especificado ya
     *                                     existe para la Escuela especificada.
     * @throws CursoExistenteException     Si el Curso con el nombre especificado ya
     *                                     existe en la base de datos.
     * @throws EscuelaNoExistenteException Si la Escuela con el ID especificado no
     *                                     existe.
     * @throws EntityDisabledException     Si la Escuela con el ID especificado está
     *                                     deshabilitada.
     */
    @Transactional
    @PostMapping(value = "/save/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<CursoResponseDto> save(@PathVariable Long id, @RequestBody @Valid CursoDto dto)
            throws CursoNoExistenteException, CursoExistenteException, EscuelaNoExistenteException,
            EntityDisabledException {
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.save(dto, id));
    }

    // ----------------------- POST END ----------------------------------

    // ----------------------- PUT START ----------------------------------

    /**
     * Habilita un Curso por su ID.
     *
     * Este método maneja una solicitud HTTP PUT para habilitar un Curso por su ID.
     *
     * @param id El ID del Curso a habilitar.
     * @return Una respuesta HTTP 200 (OK) con un mensaje de respuesta indicando que
     *         el Curso fue habilitado correctamente.
     * @throws EscuelaNoExistenteException Si la Escuela a la que pertenece el Curso
     *                                     no existe.
     * @throws CursoNoExistenteException   Si el Curso con el ID especificado no
     *                                     existe.
     * @throws EntityDisabledException     Si el Curso con el ID especificado ya
     *                                     está habilitado.
     */
    @PutMapping(value = "/enable/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<ResponseMessage> enable(@PathVariable Long id)
            throws EscuelaNoExistenteException, CursoNoExistenteException, EntityDisabledException {
        cursoService.enable(id);
        ResponseMessage responseMessage = new ResponseMessage(
                personalizedMessage.cursoEnabled().replace("$", id.toString()), 0);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    /**
     * Actualiza la información de un Curso por su ID.
     *
     * Este método maneja una solicitud HTTP PUT para actualizar la información de
     * un Curso por su ID.
     *
     * @param dto La información del Curso a actualizar.
     * @param id  El ID del Curso a actualizar.
     * @return Una respuesta HTTP 200 (OK) con el Curso actualizado en el cuerpo de
     *         la respuesta.
     * @throws EscuelaNoExistenteException Si la Escuela a la que pertenece el Curso
     *                                     no existe.
     * @throws EntityDisabledException     Si el Curso con el ID especificado está
     *                                     deshabilitado.
     * @throws CursoNoExistenteException   Si el Curso con el ID especificado no
     *                                     existe.
     */
    @PutMapping(value = "/update/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<CursoResponseDto> update(@RequestBody CursoDtoUpdate dto, @PathVariable Long id)
            throws EscuelaNoExistenteException, EntityDisabledException, CursoNoExistenteException {
        return ResponseEntity.status(HttpStatus.OK).body(cursoService.update(dto, id));
    }

    // ----------------------- PUT END ----------------------------------

    // ----------------------- DELETE START ----------------------------------

    /**
     * Deshabilita un Curso por su ID.
     *
     * Este método maneja una solicitud HTTP DELETE para deshabilitar un Curso por
     * su ID.
     *
     * @param id El ID del Curso a deshabilitar.
     * @return Una respuesta HTTP 200 (OK) con un mensaje de respuesta indicando que
     *         el Curso fue deshabilitado correctamente.
     * @throws EscuelaNoExistenteException Si la Escuela a la que pertenece el Curso
     *                                     no existe.
     * @throws EntityDisabledException     Si el Curso con el ID especificado ya
     *                                     está deshabilitado.
     * @throws CursoNoExistenteException   Si el Curso con el ID especificado no
     *                                     existe.
     */
    @DeleteMapping(value = "/disable/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<ResponseMessage> disable(@PathVariable Long id)
            throws EscuelaNoExistenteException, EntityDisabledException, CursoNoExistenteException {
        cursoService.disable(id);
        ResponseMessage responseMessage = new ResponseMessage(
                personalizedMessage.cursoDeleted().replace("$", id.toString()), 0);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    // ----------------------- DELETE END ----------------------------------
}
