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
import com.escuelas.project.escuelas_project.service.models.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.exceptions.cursoExceptions.CursoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.cursoExceptions.CursoNoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.escuelaExceptions.EscuelaNoExistenteException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cursos")
public class CursoController {
    
    private final CursoService cursoService;

    //GET
    @GetMapping(value = "/findAllActive/{id}/{year}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<CursoResponseDto>> findAllActive(@PathVariable Integer year, @PathVariable Long id) throws EntityDisabledException, EscuelaNoExistenteException {
        return ResponseEntity.status(HttpStatus.OK).body(cursoService.findAllActiveDto(year, id));
    }

    @GetMapping(value = "/findByNombre/{nombre}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<CursoResponseDto> findByNombre(@PathVariable String nombre) throws CursoNoExistenteException {
        return ResponseEntity.status(HttpStatus.OK).body(cursoService.findByNombreDto(nombre));
    }

    //POST
    @Transactional
    @PostMapping(value = "/save/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<CursoResponseDto> save(@PathVariable Long id, @RequestBody @Valid CursoDto dto) throws CursoNoExistenteException, CursoExistenteException, EscuelaNoExistenteException, EntityDisabledException {
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.save(dto, id));
    }

    //PUT
    @PutMapping(value = "/enable/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<?> enable(@PathVariable Long id) throws EscuelaNoExistenteException, CursoNoExistenteException, EntityDisabledException {
        cursoService.enable(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/update/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<CursoResponseDto> update(@RequestBody CursoDtoUpdate dto, @PathVariable Long id) throws EscuelaNoExistenteException, EntityDisabledException, CursoNoExistenteException {
        return ResponseEntity.status(HttpStatus.OK).body(cursoService.update(dto, id));
    }

    //DELETE
    @DeleteMapping(value = "/disable/{id}", headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<?> disable(@PathVariable Long id) throws EscuelaNoExistenteException, EntityDisabledException, CursoNoExistenteException {
        cursoService.disable(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
