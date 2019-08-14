package com.greenfox.javatribes.javatribes.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class RequestObject {

    @NotNull
    @NotEmpty
    @JsonAlias({"type", "name", "level"})
    private String field;

    public RequestObject(String field) {
        this.field = field;
    }
}
