package com.greenfox.javatribes.javatribes.dto;

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
    private String field;

    public RequestObject(String field) {
        this.field = field;
    }
}
