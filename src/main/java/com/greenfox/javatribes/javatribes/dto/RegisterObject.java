package com.greenfox.javatribes.javatribes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("kingdom")
    private String kingdomName;

    public RegisterObject(String username, String password, String kingdomName) {
        this.username = username;
        this.password = password;
        this.kingdomName = kingdomName;
    }
}
