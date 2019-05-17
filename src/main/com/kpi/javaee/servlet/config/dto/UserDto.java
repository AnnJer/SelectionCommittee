package com.kpi.javaee.servlet.config.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserDto {

    private String name;
    private String surname;
    private String lastname;
    private String login;
    private final List<String> roles;


    @JsonCreator
    public UserDto(@JsonProperty("name") String name,
                   @JsonProperty("surname") String surname,
                   @JsonProperty("lastname") String lastname,
                   @JsonProperty("login") String login,
                   @JsonProperty("roles") List<String> roles
    ) {
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.login = login;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public List<String> getRoles() {
        return roles;
    }
}