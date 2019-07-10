package com.greenfox.javatribes.javatribes.exceptions;

import com.greenfox.javatribes.javatribes.dto.ResponseObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(HttpServletResponse res, CustomException ex) throws IOException {
        res.sendError(ex.getHttpStatus().value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.valueOf(ex.getHttpStatus().value())).body(new ResponseObject("error",
                ex.getMessage()));
    }

//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<Object> handleEntityNotFoundException(
//            EntityNotFoundException ex){
//            return ResponseEntity.status(HttpStatus.valueOf(401)).body(new ResponseObject("error",
//                    ex.getMessage()));
//    }
//
//    @ExceptionHandler(IdentityAlreadyUsedException.class)
//    public ResponseEntity<Object> handleIdentityAlreadyUsedException(
//            IdentityAlreadyUsedException ex){
//        return ResponseEntity.status(HttpStatus.valueOf(409)).body(new ResponseObject("error",
//                ex.getMessage()));
//    }
}
