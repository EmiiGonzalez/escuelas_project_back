package com.escuelas.project.escuelas_project.service.curso;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.escuelas.project.escuelas_project.persistence.entity.Curso;
import com.escuelas.project.escuelas_project.persistence.entity.Escuela;
import com.escuelas.project.escuelas_project.persistence.repository.CursoRepository;
import com.escuelas.project.escuelas_project.persistence.repository.EscuelaRepository;
import com.escuelas.project.escuelas_project.service.models.dtos.curso.CursoDto;
import com.escuelas.project.escuelas_project.service.models.dtos.curso.CursoDtoUpdate;
import com.escuelas.project.escuelas_project.service.models.dtos.curso.CursoResponseDto;
import com.escuelas.project.escuelas_project.service.models.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.exceptions.curso.CursoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.curso.CursoNoExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.escuelaExceptions.EscuelaNoExistenteException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CursoServiceImp implements CursoService {

    private final CursoRepository cursoRepository;
    private final EscuelaRepository escuelaRepository;

    @Override
    public List<CursoResponseDto> findAllActiveDto(Integer year, Long id) throws EntityDisabledException, EscuelaNoExistenteException {
        Optional<Escuela> escuela = this.escuelaRepository.findById(id);

        if (!escuela.isPresent()) {
            throw new EscuelaNoExistenteException("La escuela no existe");
        }
        Escuela escuelaEntity = escuela.get();

        if (!escuelaEntity.getActivo()) {
            throw new EntityDisabledException("La escuela esta deshabilitada");
        }
        

        return this.cursoRepository.findAllActiveDto(year, escuelaEntity);
    }

    @Override
    public CursoResponseDto findByNombreDto(String nombre) throws CursoNoExistenteException {
        Optional<CursoResponseDto> curso = this.cursoRepository.findByNombreDto(nombre);
        if (curso.isEmpty()) {
            throw new CursoNoExistenteException("El curso no existe");
        }
        CursoResponseDto dto = curso.get();
        return dto;
    }

    @Override
    public CursoResponseDto save(CursoDto dto, Long id) throws CursoExistenteException, EscuelaNoExistenteException {
        Optional<Escuela> escuelaOptional = this.escuelaRepository.findById(id);

        if (escuelaOptional.isEmpty()) {
            throw new EscuelaNoExistenteException("La escuela no existe");
        }

        if (this.cursoRepository.findByNombreActivo(dto.nombre()).isPresent()) {
            throw new CursoExistenteException("El curso ya existe");
        }

        Escuela escuela = escuelaOptional.get();
        Curso curso = new Curso(dto, escuela);

        return new CursoResponseDto(this.cursoRepository.save(curso));
    }

    @Override
    public void enable(Long id) throws CursoNoExistenteException, EntityDisabledException {
        Optional<Curso> cursoOptional = this.cursoRepository.findById(id);

        if (cursoOptional.isEmpty()) {
            throw new CursoNoExistenteException("El curso no existe");
        }

        Curso curso = cursoOptional.get();

        if (curso.getActivo()) {
            throw new EntityDisabledException("El curso ya estaba habilitado");
        }

        curso.setActivo(true);
    }

    @Override
    public void disable(Long id) throws CursoNoExistenteException, EntityDisabledException {
        Optional<Curso> cursoOptional = this.cursoRepository.findById(id);

        if (cursoOptional.isEmpty()) {
            throw new CursoNoExistenteException("El curso no existe");
        }

        Curso curso = cursoOptional.get();

        if (!curso.getActivo()) {
            throw new EntityDisabledException("El curso ya estaba deshabilitado");
        }

        curso.setActivo(false);
    }

    @Override
    public CursoResponseDto update(CursoDtoUpdate dto, Long id) throws CursoNoExistenteException, EntityDisabledException {
        Optional<Curso> cursoOptional = this.cursoRepository.findById(id);

        if (cursoOptional.isEmpty()) {
            throw new CursoNoExistenteException("El curso no existe");
        }

        Curso curso = cursoOptional.get();
        curso.update(dto);

        return new CursoResponseDto(this.cursoRepository.save(curso));
    }
    
    
    
}
