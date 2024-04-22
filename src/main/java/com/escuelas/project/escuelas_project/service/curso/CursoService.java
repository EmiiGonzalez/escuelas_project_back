package com.escuelas.project.escuelas_project.service.curso;

import java.util.List;

import com.escuelas.project.escuelas_project.service.models.dtos.curso.CursoDto;
import com.escuelas.project.escuelas_project.service.models.dtos.curso.CursoDtoUpdate;
import com.escuelas.project.escuelas_project.service.models.dtos.curso.CursoResponseDto;
import com.escuelas.project.escuelas_project.service.models.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.exceptions.curso.CursoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.curso.CursoNoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.escuelaExceptions.EscuelaNoExistenteException;

public interface CursoService {

    List<CursoResponseDto> findAllActiveDto(Integer year, Long id) throws EntityDisabledException, EscuelaNoExistenteException;
    CursoResponseDto findByNombreDto(String nombre) throws CursoNoExistenteException;

    CursoResponseDto save(CursoDto dto, Long id) throws CursoExistenteException, EscuelaNoExistenteException;

    void enable(Long id) throws CursoNoExistenteException, EntityDisabledException;

    void disable(Long id) throws CursoNoExistenteException, EntityDisabledException;

    CursoResponseDto update(CursoDtoUpdate dto, Long id) throws CursoNoExistenteException, EntityDisabledException;
}
