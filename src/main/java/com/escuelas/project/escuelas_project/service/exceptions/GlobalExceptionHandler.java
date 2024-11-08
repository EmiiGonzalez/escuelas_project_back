package com.escuelas.project.escuelas_project.service.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.escuelas.project.escuelas_project.alumno.exceptions.AlumnoNoExistenteException;
import com.escuelas.project.escuelas_project.asistencia.exceptions.AsistenciaExistenteException;
import com.escuelas.project.escuelas_project.asistencia.exceptions.AsistenciaNoExistenteException;
import com.escuelas.project.escuelas_project.clase.exceptions.ClaseNoExistenteException;
import com.escuelas.project.escuelas_project.curso.exceptions.CursoExistenteException;
import com.escuelas.project.escuelas_project.curso.exceptions.CursoNoExistenteException;
import com.escuelas.project.escuelas_project.escuela.exceptions.EscuelaExistenteException;
import com.escuelas.project.escuelas_project.escuela.exceptions.EscuelaNoExistenteException;
import com.escuelas.project.escuelas_project.service.models.dtos.response.ResponseMessage;
import com.escuelas.project.escuelas_project.service.utils.PersonalizedMessage;

import lombok.AllArgsConstructor;

@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {
    private PersonalizedMessage personalizedMessage;

    // VALIDATION EXCEPTIONS DTOS START
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ResponseMessage>> validationError(MethodArgumentNotValidException e) {
        List<ResponseMessage> errorsList = new ArrayList<>();
        e.getFieldErrors().stream().forEach(error -> {
            ResponseMessage responseMessage = new ResponseMessage(error.getDefaultMessage(), 1);
            errorsList.add(responseMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsList);
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseMessage> validationError(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(personalizedMessage.bodyNotValid(), 1));
    }

    // VALIDATION EXCEPTIONS DTOS END

    // ENTITY EXCEPTIONS START

    @ExceptionHandler(EntityDisabledException.class)
    @ResponseBody
    public ResponseEntity<ResponseMessage> entityDisabledException(EntityDisabledException e) {
        ResponseMessage responseMessage = new ResponseMessage(personalizedMessage.errorNotEntity().replace("$", e.getMessage()), 2);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
    }

    // ENTITY EXCEPTIONS END

    // VALIDATION ROUTES NOT FOUND AND BAD ARGUMENTS START

    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseMessage> validationError(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(personalizedMessage.badArgument(), 5));
    }

    @ResponseBody
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ResponseMessage> validationErrorRutaNoMapeada(NoHandlerFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(personalizedMessage.routeNotFound(), 6));
    }

    // VALIDATION ROUTES NOT FOUND AND BAD ARGUMENTS END

    // CURSO EXCEPTIONS START
    @ResponseBody
    @ExceptionHandler(CursoNoExistenteException.class)
    public ResponseEntity<ResponseMessage> cursoNoExistenteException(CursoNoExistenteException e) {
        ResponseMessage responseMessage = new ResponseMessage(personalizedMessage.cursoNotFound(), 3);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
    }

    @ResponseBody
    @ExceptionHandler(CursoExistenteException.class)
    public ResponseEntity<ResponseMessage> cursoExistenteException (CursoExistenteException e) {
        ResponseMessage responseMessage = new ResponseMessage(personalizedMessage.cursoAlredyExist(), 4);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
    }

    // CURSO EXCEPTIONS END

    // ESCUELA EXCEPTIONS START

    @ResponseBody
    @ExceptionHandler(EscuelaNoExistenteException.class)
    public ResponseEntity<ResponseMessage> escuelaNoExistenteException(EscuelaNoExistenteException e) {
        ResponseMessage responseMessage = new ResponseMessage(personalizedMessage.escuelaNotFound(), 3);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
    }

    @ResponseBody
    @ExceptionHandler(EscuelaExistenteException.class)
    public ResponseEntity<ResponseMessage> escuelaExistenteException(EscuelaExistenteException e) {
        ResponseMessage responseMessage = new ResponseMessage(personalizedMessage.escuelaAlredyExist(), 4);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
    }

    // ESCUELA EXCEPTIONS END

    // CLASES EXCEPTIONS START

    @ResponseBody
    @ExceptionHandler(ClaseNoExistenteException.class)
    public ResponseEntity<ResponseMessage> claseNoExistenteException(ClaseNoExistenteException e) {
        ResponseMessage responseMessage = new ResponseMessage(personalizedMessage.claseNotFound(), 3);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
    }

    // CLASES EXCEPTIONS END

    // ASISTENCIA EXCEPTIONS START

    @ResponseBody
    @ExceptionHandler(AsistenciaNoExistenteException.class)
    public ResponseEntity<ResponseMessage> asistenciaNoExistenteException(AsistenciaNoExistenteException e) {
        ResponseMessage responseMessage = new ResponseMessage(personalizedMessage.asistenciaNotFound(), 3);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
    }

    @ResponseBody
    @ExceptionHandler(AsistenciaExistenteException.class)
    public ResponseEntity<ResponseMessage> asistenciaExistenteException(AsistenciaExistenteException e) {
        ResponseMessage responseMessage = new ResponseMessage(personalizedMessage.asistenciaAlredyExist(), 4);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
    }

    // ASISTENCIA EXCEPTIONS END

    //ALUMNO EXCEPTIONS START

    @ResponseBody
    @ExceptionHandler(AlumnoNoExistenteException.class)
    public ResponseEntity<ResponseMessage> alumnoNoExistenteException(AlumnoNoExistenteException e) {
        ResponseMessage responseMessage = new ResponseMessage(personalizedMessage.alumnoNotFound(), 3);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
    }

    //ALUMNO EXCEPTIONS END

}
