package com.greenfox.javatribes.javatribes.model;

public class RegisterObject {

    private String password;
    private String username;
    private String kingdom;

    public RegisterObject() {
    }

    public RegisterObject(String password, String username, String kingdom) {
        this.password = password;
        this.username = username;
        this.kingdom = kingdom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKingdom() {
        return kingdom;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }
}
