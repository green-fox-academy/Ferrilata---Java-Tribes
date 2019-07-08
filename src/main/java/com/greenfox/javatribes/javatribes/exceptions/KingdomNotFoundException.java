package com.greenfox.javatribes.javatribes.exceptions;

public class KingdomNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    private String message;

    public KingdomNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}