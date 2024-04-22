package com.escuelas.project.escuelas_project.service.escuela;

import java.util.List;

import com.escuelas.project.escuelas_project.service.models.dtos.escuela.EscuelaDto;
import com.escuelas.project.escuelas_project.service.models.dtos.escuela.EscuelaDtoUpdate;
import com.escuelas.project.escuelas_project.service.models.dtos.escuela.EscuelaResponseDto;
import com.escuelas.project.escuelas_project.service.models.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.exceptions.escuelaExceptions.EscuelaExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.escuelaExceptions.EscuelaNoExistenteException;

public interface EscuelaService {

    EscuelaResponseDto save(EscuelaDto escuela) throws EscuelaExistenteException;
    EscuelaResponseDto findById(Long id) throws EscuelaNoExistenteException, EntityDisabledException;
    EscuelaResponseDto update(EscuelaDtoUpdate escuela, Long id) throws EscuelaNoExistenteException, EntityDisabledException;
    List<EscuelaResponseDto> findAllActive();
    List<EscuelaResponseDto> findAll();
    void disable(Long id) throws EscuelaNoExistenteException, EntityDisabledException;
    void enable(Long id) throws EscuelaNoExistenteException, EntityDisabledException;

}
