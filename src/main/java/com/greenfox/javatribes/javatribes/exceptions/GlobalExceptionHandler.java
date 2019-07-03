package com.greenfox.javatribes.javatribes.exceptions;

import com.greenfox.javatribes.javatribes.model.ResponseObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        String notValidFields = fieldErrors.stream()
                .map(FieldError::getField)
                .distinct()
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.valueOf(400)).body(new ResponseObject("error",
                "Missing parameter(s): " + notValidFields));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(
            EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.valueOf(401)).body(new ResponseObject("error",
                    ex.getMessage()));
    }

    @ExceptionHandler(UsernameAlreadyUsedException.class)
    public ResponseEntity<Object> handleUsernameAlreadyUsedException(
            UsernameAlreadyUsedException ex){
        return ResponseEntity.status(HttpStatus.valueOf(409)).body(new ResponseObject("error",
                ex.getMessage()));
    }
}
