package com.greenfox.javatribes.javatribes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class RegisterObject {

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    private String kingdom;

    public RegisterObject(String username, String password, String kingdom) {
        this.username = username;
        this.password = password;
        this.kingdom = kingdom;
    }
}
