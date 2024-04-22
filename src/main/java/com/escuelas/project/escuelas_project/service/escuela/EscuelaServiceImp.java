package com.escuelas.project.escuelas_project.service.escuela;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.escuelas.project.escuelas_project.persistence.entity.Escuela;
import com.escuelas.project.escuelas_project.persistence.repository.EscuelaRepository;
import com.escuelas.project.escuelas_project.service.models.dtos.escuela.EscuelaDto;
import com.escuelas.project.escuelas_project.service.models.dtos.escuela.EscuelaDtoUpdate;
import com.escuelas.project.escuelas_project.service.models.dtos.escuela.EscuelaResponseDto;
import com.escuelas.project.escuelas_project.service.models.exceptions.EntityDisabledException;
import com.escuelas.project.escuelas_project.service.models.exceptions.escuelaExceptions.EscuelaExistenteException;
import com.escuelas.project.escuelas_project.service.models.exceptions.escuelaExceptions.EscuelaNoExistenteException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EscuelaServiceImp implements EscuelaService{

    private final EscuelaRepository escuelaRepository;

    @Override
    public EscuelaResponseDto save(EscuelaDto escuela) throws EscuelaExistenteException {
        Optional<Escuela> escuelaOptional = escuelaRepository.findByNombre(escuela.nombre());
        if (escuelaOptional.isPresent()) {
            throw new EscuelaExistenteException("La escuela ya existe en la base de datos");
        }

        return new EscuelaResponseDto(escuelaRepository.save(new Escuela(escuela.nombre())));
    }

    @Override
    public EscuelaResponseDto findById(Long id) throws EscuelaNoExistenteException, EntityDisabledException {
        Optional<Escuela> escuelaOptional = escuelaRepository.findByIdActive(id);
        if (!escuelaOptional.isPresent()) {
            throw new EscuelaNoExistenteException("La escuela no existe en la base de datos");
        }

        Escuela escuela = escuelaOptional.get();
        if (!escuela.getActivo()) {
            throw new EntityDisabledException("La escuela esta deshabilitada");
        }

        return new EscuelaResponseDto(escuela);
    }

    @Override
    public EscuelaResponseDto update(EscuelaDtoUpdate escuela, Long id) throws EscuelaNoExistenteException, EntityDisabledException {
        Optional<Escuela> escuelaOptional = escuelaRepository.findById(id);
        if (!escuelaOptional.isPresent()) {
            throw new EscuelaNoExistenteException("La escuela no existe en la base de datos");
        }
        Escuela escuelaUpdate = escuelaOptional.get();
        escuelaUpdate.update(escuela);

        return new EscuelaResponseDto(escuelaUpdate);
    }

    @Override
    public List<EscuelaResponseDto> findAllActive() {
        return escuelaRepository.findAllActive();
    }

    @Override
    public List<EscuelaResponseDto> findAll() {
        return escuelaRepository.findAll().stream().map(EscuelaResponseDto::new).toList();
    }

    @Override
    public void disable(Long id) throws EscuelaNoExistenteException, EntityDisabledException {
        Optional<Escuela> escuelaOptional = escuelaRepository.findById(id);
        if (!escuelaOptional.isPresent()) {
            throw new EscuelaNoExistenteException("La escuela no existe en la base de datos");
        }
        Escuela escuela = escuelaOptional.get();
        
        if (!escuela.getActivo()) {
            throw new EntityDisabledException("La escuela ya estaba deshabilitada");
        }

        escuela.setActivo(false);
    }

    @Override
    public void enable(Long id) throws EscuelaNoExistenteException, EntityDisabledException {
        Optional<Escuela> escuelaOptional = escuelaRepository.findById(id);
        if (!escuelaOptional.isPresent()) {
            throw new EscuelaNoExistenteException("La escuela no existe en la base de datos");
        }
        Escuela escuela = escuelaOptional.get();
        
        if (escuela.getActivo()) {
            throw new EntityDisabledException("La escuela ya estaba habilitada");
        }

        escuela.setActivo(true);
    }


    
}
