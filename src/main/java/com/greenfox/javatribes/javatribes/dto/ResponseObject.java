package com.greenfox.javatribes.javatribes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseObject {

    private String status;
    private String message;
    private String token;

    public ResponseObject(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseObject(String status, String message, String token) {
        this.status = status;
        this.message = message;
        this.token = token;
    }

}
