package com.kpi.javaee.servlet.exceptions;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalUserInputException extends RuntimeException {

    @JsonCreator
    public IllegalUserInputException(@JsonProperty("error") String message) {
        super(message);
    }

    public IllegalUserInputException() {
        super("Illegal input");
    }
}
