package com.escuelas.project.escuelas_project.service.clase;


import java.util.List;

import com.escuelas.project.escuelas_project.service.models.dtos.clase.ClaseCountResponseDto;
import com.escuelas.project.escuelas_project.service.models.dtos.clase.ClaseDto;
import com.escuelas.project.escuelas_project.service.models.dtos.clase.ClaseResponseDto;
import com.escuelas.project.escuelas_project.service.models.dtos.clase.ClaseUpdateDto;
import com.escuelas.project.escuelas_project.service.models.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.exceptions.clasesExceptions.ClaseExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.clasesExceptions.ClaseNoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.cursoExceptions.CursoNoExistenteException;


public interface ClaseService {
    
    public ClaseResponseDto save(ClaseDto dto, Long id) throws ClaseExistenteException, CursoNoExistenteException, EntityDisabledException;

    public ClaseResponseDto update(ClaseUpdateDto dto, Long id) throws ClaseNoExistenteException, EntityDisabledException;

    public ClaseResponseDto findById(Long id) throws ClaseNoExistenteException, EntityDisabledException;

    public void deleteById(Long id) throws ClaseNoExistenteException, EntityDisabledException;

    public List<ClaseResponseDto> findAll(Long id) throws CursoNoExistenteException, EntityDisabledException;

    public ClaseCountResponseDto count(Long id) throws CursoNoExistenteException, EntityDisabledException;

}
