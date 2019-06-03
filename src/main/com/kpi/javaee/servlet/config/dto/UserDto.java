package com.kpi.javaee.servlet.config.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kpi.javaee.servlet.entities.RateFactorResultEntity;

import java.util.List;

public class UserDto {

    private Long id;
    private String name;
    private String surname;
    private String lastname;
    private String login;
    private List<RateFactorResultEntity> rateFactors;
    private final List<String> roles;


    @JsonCreator
    public UserDto(@JsonProperty("id") Long id,
                   @JsonProperty("name") String name,
                   @JsonProperty("surname") String surname,
                   @JsonProperty("lastname") String lastname,
                   @JsonProperty("login") String login,
                   @JsonProperty("rate_factors") List<RateFactorResultEntity> rateFactors,
                   @JsonProperty("roles") List<String> roles
    ) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.login = login;
        this.rateFactors = rateFactors;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RateFactorResultEntity> getRateFactors() {
        return rateFactors;
    }

    public void setRateFactors(List<RateFactorResultEntity> rateFactors) {
        this.rateFactors = rateFactors;
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