package com.greenfox.javatribes.javatribes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RegisterObject {

    @NotNull
    @NotEmpty
    @ToString.Exclude
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
