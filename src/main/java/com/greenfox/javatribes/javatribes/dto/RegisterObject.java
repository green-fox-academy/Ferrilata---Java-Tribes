package com.greenfox.javatribes.javatribes.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RegisterObject {

    @NotNull @NotEmpty
    private String password;

    @NotNull @NotEmpty
    private String username;

    @NotNull @NotEmpty
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
