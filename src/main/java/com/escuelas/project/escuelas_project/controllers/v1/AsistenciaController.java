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

import com.escuelas.project.escuelas_project.service.asistencia.AsistenciaService;
import com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaDto;
import com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaResponseDto;
import com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaResponsePorClaseDto;
import com.escuelas.project.escuelas_project.service.models.dtos.asistencia.AsistenciaUpdateDto;
import com.escuelas.project.escuelas_project.service.models.dtos.response.ResponseMessage;
import com.escuelas.project.escuelas_project.service.models.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.exceptions.alumnoExceptions.AlumnoNoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.asistenciaExceptions.AsistenciaNoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.clasesExceptions.ClaseNoExistenteException;
import com.escuelas.project.escuelas_project.service.utils.PersonalizedMessage;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/asistencias")
@AllArgsConstructor
public class AsistenciaController {
    
    private final AsistenciaService asistenciaService;
    private PersonalizedMessage personalizedMessage;

    //POST

    @PostMapping("/save/{alumnoId}/{claseId}")
    @ResponseBody
    @Transactional
    public ResponseEntity<AsistenciaResponseDto> save(@PathVariable Long claseId, @PathVariable Long alumnoId, @RequestBody AsistenciaDto dto) throws AlumnoNoExistenteException, EntityDisabledException, ClaseNoExistenteException {
        return ResponseEntity.status(HttpStatus.CREATED).body(asistenciaService.createAsistencia(dto, alumnoId, claseId));
    }

    //GET

    @GetMapping("/find/{id}")
    @ResponseBody
    public ResponseEntity<AsistenciaResponseDto> findById(@PathVariable Long id) throws AsistenciaNoExistenteException, EntityDisabledException {
        return ResponseEntity.ok(asistenciaService.findByIdAsistencia(id));
    }

    @GetMapping("/find/all/alumno/{id}")
    @ResponseBody
    public ResponseEntity<List<AsistenciaResponseDto>> findAllByAlumno(@PathVariable Long id) throws EntityDisabledException, AlumnoNoExistenteException {
        return ResponseEntity.ok(asistenciaService.findAllAsistenciaDtoByAlumno(id));
    }

    @GetMapping("/find/all/clase/{id}")
    @ResponseBody
    public ResponseEntity<List<AsistenciaResponsePorClaseDto>> findAllByClase(@PathVariable Long id) throws ClaseNoExistenteException, EntityDisabledException {
        return ResponseEntity.ok(asistenciaService.findAllAsistenciaDtoByClase(id));
    }

    //PUT

    @PutMapping("/update/{id}")
    @ResponseBody
    @Transactional
    public ResponseEntity<AsistenciaResponseDto> update(@RequestBody AsistenciaUpdateDto dto, @PathVariable Long id) throws AsistenciaNoExistenteException, EntityDisabledException{
        return ResponseEntity.status(HttpStatus.OK).body(asistenciaService.updateAsistencia(id, dto));
    }

    //DELETE

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    @Transactional
    public ResponseEntity<ResponseMessage> delete(@PathVariable Long id) throws AsistenciaNoExistenteException, EntityDisabledException {
        asistenciaService.deleteAsistencia(id);
        ResponseMessage responseMessage = new ResponseMessage(personalizedMessage.asistenciaDeleted().replace("$", id.toString()), 0);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

}
