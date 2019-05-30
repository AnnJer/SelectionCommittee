package com.kpi.javaee.servlet.config.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionDto {
    private String token;
    private UserDto user;

    @JsonCreator
    public SessionDto(@JsonProperty("token") String token,
                      @JsonProperty("user") UserDto user) {
        this.token = token;
        this.user = user;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
