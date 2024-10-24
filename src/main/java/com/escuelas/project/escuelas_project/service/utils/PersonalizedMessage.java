package com.escuelas.project.escuelas_project.service.utils;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PersonalizedMessage {
    private final MessageSource messageSource;

    // ALUMNO MESSAGE STARTS
    public String alumnoNotFound(){
        return messageSource.getMessage("alumno.not.found", null, null);
    }

    public String alumnoDeleted(){
        return messageSource.getMessage("alumno.delete.success", null, null);
    }

    public String alumnoEnabled() {
        return messageSource.getMessage("alumno.enable.success", null, null);
    }

    // ALUMNO MESSAGE ENDS

    // ESCUELA MESSAGE STARTS

    public String escuelaNotFound(){
        return messageSource.getMessage("escuela.not.found", null, null);
    }

    public String escuelaDeleted(){
        return messageSource.getMessage("escuela.delete.success", null, null);
    }

    public String escuelaAlredyExist(){
        return messageSource.getMessage("escuela.already.exists", null, null);
    }

    public String escuelaEnabled() {
        return messageSource.getMessage("escuela.enable.success", null, null);
    }

    // ESCUELA MESSAGE ENDS

    // CURSO MESSAGE STARTS

    public String cursoNotFound(){
        return messageSource.getMessage("curso.not.found", null, null);
    }

    public String cursoDeleted(){
		return messageSource.getMessage("curso.delete.success", null, null);
    }

    public String cursoAlredyExist(){
        return messageSource.getMessage("curso.already.exists", null, null);
    }

    public String cursoEnabled() {
        return messageSource.getMessage("curso.enable.success", null, null);
    }

    // CURSO MESSAGE ENDS

    // CLASE MESSAGE STARTS

    public String claseNotFound(){
        return messageSource.getMessage("clase.not.found", null, null);
    }

    public String claseDeleted(){
        return messageSource.getMessage("clase.delete.success", null, null);
    }

    // CLASE MESSAGE ENDS

    // ASISTENCIA MESSAGE STARTS

    public String asistenciaNotFound(){
        return messageSource.getMessage("asistencia.not.found", null, null);
    }

    public String asistenciaDeleted(){
        return messageSource.getMessage("asistencia.delete.success", null, null);
    }

    // ASISTENCIA MESSAGE ENDS

    // ERRORS MESSAGE STARTS
    
    public String errorNotEntity(){
        return messageSource.getMessage("entity.disable.message", null, null);
    }

    public String badArgument() {
        return messageSource.getMessage("bad.argument", null, null);
    }

    public String routeNotFound() {
        return messageSource.getMessage("route.not.found", null, null);
    }

    public String bodyNotValid() {
        return messageSource.getMessage("body.not.valid", null, null);
    }

    public String asistenciaAlredyExist() {
        return messageSource.getMessage("asistencia.already.exists", null, null);
    }

    // ERRORS MESSAGE ENDS
}
