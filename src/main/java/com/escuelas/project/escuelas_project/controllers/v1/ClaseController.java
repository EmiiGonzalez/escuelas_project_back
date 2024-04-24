package com.escuelas.project.escuelas_project.controllers.v1;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.escuelas.project.escuelas_project.service.clase.ClaseService;
import com.escuelas.project.escuelas_project.service.models.dtos.clase.ClaseCountResponseDto;
import com.escuelas.project.escuelas_project.service.models.dtos.clase.ClaseDto;
import com.escuelas.project.escuelas_project.service.models.dtos.clase.ClaseResponseDto;
import com.escuelas.project.escuelas_project.service.models.dtos.clase.ClaseUpdateDto;
import com.escuelas.project.escuelas_project.service.models.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.exceptions.clasesExceptions.ClaseExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.clasesExceptions.ClaseNoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.cursoExceptions.CursoNoExistenteException;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/clases")
@AllArgsConstructor
public class ClaseController {

    private final ClaseService claseService;

    // GET

    @GetMapping(value = "/findAll/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<ClaseResponseDto>> findAll(@PathVariable Long id) throws CursoNoExistenteException, EntityDisabledException {
        return ResponseEntity.status(HttpStatus.OK).body(claseService.findAll(id));
    }

    @GetMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<ClaseResponseDto> findById(@PathVariable Long id) throws ClaseNoExistenteException {
        return ResponseEntity.status(HttpStatus.OK).body(claseService.findById(id));
    }

    @GetMapping(value = "/count/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<ClaseCountResponseDto> count(@PathVariable Long id) throws CursoNoExistenteException, EntityDisabledException {
        return ResponseEntity.status(HttpStatus.OK).body(claseService.count(id));
    }

    // POST

    @PostMapping(value = "/save/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<ClaseResponseDto> save(@PathVariable Long id, @RequestBody ClaseDto dto)
            throws ClaseExistenteException, CursoNoExistenteException, EntityDisabledException {
        return ResponseEntity.status(HttpStatus.CREATED).body(claseService.save(dto, id));
    }

    // PUT

    @PutMapping(value = "/update/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<ClaseResponseDto> update(@PathVariable Long id, @RequestBody ClaseUpdateDto dto)
            throws ClaseNoExistenteException {
        return ResponseEntity.status(HttpStatus.OK).body(claseService.update(dto, id));
    }

    // DELETE

    @PutMapping(value = "/delete/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) throws ClaseNoExistenteException {
        claseService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
