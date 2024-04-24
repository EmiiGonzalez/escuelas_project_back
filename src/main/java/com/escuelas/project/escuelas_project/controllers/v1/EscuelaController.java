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

@RestController
@RequestMapping("/api/v1/escuelas")
@AllArgsConstructor
public class EscuelaController {
    private final EscuelaService escuelaService;
    private PersonalizedMessage personalizedMessage;

    //POST
    @PostMapping(value = "/save", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<EscuelaResponseDto> save(@RequestBody @Valid EscuelaDto escuela) throws EscuelaExistenteException {
        System.out.println(escuela);
        return ResponseEntity.status(HttpStatus.CREATED).body(escuelaService.save(escuela));
    }

    //GET
    @GetMapping(value = "/find/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<EscuelaResponseDto> findById(@PathVariable Long id) throws EscuelaNoExistenteException, EntityDisabledException {
        return ResponseEntity.ok(escuelaService.findById(id));
    }

    @GetMapping(value = "/find/all/active", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<EscuelaResponseDto>> findAllActive() {
        return ResponseEntity.status(HttpStatus.OK).body(escuelaService.findAllActive());
    }

    @GetMapping(value = "/find/all", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<EscuelaResponseDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(escuelaService.findAll());
    }

    //DELETE
    @DeleteMapping(value = "/disable/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<ResponseMessage> disable(@PathVariable Long id) throws EscuelaNoExistenteException, EntityDisabledException {
        escuelaService.disable(id);
        ResponseMessage responseMessage = new ResponseMessage(personalizedMessage.escuelaDeleted().replace("$", id.toString()), 0);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    //PUT
    @PutMapping(value = "/update/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<EscuelaResponseDto> update(@RequestBody EscuelaDtoUpdate escuela, @PathVariable Long id) throws EscuelaNoExistenteException, EntityDisabledException {
        return ResponseEntity.status(HttpStatus.OK).body(escuelaService.update(escuela, id));
    }

    @PutMapping(value = "/enable/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<ResponseMessage> enable(@PathVariable Long id) throws EscuelaNoExistenteException, EntityDisabledException {
        escuelaService.enable(id);
        ResponseMessage responseMessage = new ResponseMessage(personalizedMessage.escuelaEnabled().replace("$", id.toString()), 0);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
