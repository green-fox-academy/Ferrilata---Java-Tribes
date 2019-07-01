package com.greenfox.javatribes.javatribes.exceptions;

public class UsernameAlreadyUsedException extends Exception {

    private static final long serialVersionUID = 1L;

    private String message;

    public UsernameAlreadyUsedException(String message) {
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
