package com.greenfox.javatribes.javatribes.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public String handleInvalidFieldException(InvalidFieldException exception){

        return exception.getMessage();
    }
}
