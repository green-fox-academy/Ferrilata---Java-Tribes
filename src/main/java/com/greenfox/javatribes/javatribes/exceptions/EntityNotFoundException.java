package com.greenfox.javatribes.javatribes.exceptions;

public class EntityNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    private String message;

    public EntityNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
