package com.escuelas.project.escuelas_project.service.alumno;


import java.util.List;

import com.escuelas.project.escuelas_project.service.models.dtos.alumno.AlumnoDto;
import com.escuelas.project.escuelas_project.service.models.dtos.alumno.AlumnoDtoUpdate;
import com.escuelas.project.escuelas_project.service.models.dtos.alumno.AlumnoResponseDto;
import com.escuelas.project.escuelas_project.service.models.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.exceptions.alumnoExceptions.AlumnoNoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.cursoExceptions.CursoNoExistenteException;

public interface AlumnoService {

    public AlumnoResponseDto create(AlumnoDto dto, Long id) throws CursoNoExistenteException, EntityDisabledException;

    public AlumnoResponseDto update(AlumnoDtoUpdate dto, Long id) throws AlumnoNoExistenteException, EntityDisabledException;

    public void disable(Long id) throws AlumnoNoExistenteException, EntityDisabledException;

    public void enable(Long id) throws AlumnoNoExistenteException, EntityDisabledException;

    public AlumnoResponseDto findById(Long id) throws AlumnoNoExistenteException, EntityDisabledException;

    public List<AlumnoResponseDto> findAllActiveCurso(Long id) throws EntityDisabledException, CursoNoExistenteException;

    public List<AlumnoResponseDto> findAllActive();

    public List<AlumnoResponseDto> findAll();
}
