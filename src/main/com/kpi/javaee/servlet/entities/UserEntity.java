package com.kpi.javaee.servlet.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users", schema = "public", catalog = "selectioncommittee")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String surname;
    private String lastname;
    private String login;
    private String password;
    private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "usersByIdUser")
    private Collection<ApplicationEntity> applicationsById;

    @JsonIgnore
    @OneToMany(mappedBy = "usersByIdUser", fetch = FetchType.EAGER)
    private Collection<RateFactorResultEntity> rateFactorResultsById;

    @JsonIgnore
    @OneToMany(mappedBy = "usersByIdUser")
    private Collection<SessionsEntity> sessionsById;

    @JsonIgnore
    @OneToMany(mappedBy = "usersByIdEnrollee")
    private Collection<StatementEntity> statementsById;


    public UserEntity() {
    }

    @JsonCreator
    public UserEntity(
            @JsonProperty("name") String name,
            @JsonProperty("surname") String surname,
            @JsonProperty("lastname") String lastname,
            @JsonProperty("login") String login,
            @JsonProperty("role") String role
    ) {
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname", nullable = false, length = 30)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "lastname", nullable = false, length = 30)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "login", nullable = false, length = 30)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Collection<Role> getAuthorities() {
        return List.of(Role.parseFromString(role));
    }

    @Basic
    @Column(name = "password", nullable = false, length = 60)
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return login;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "role", nullable = false, length = 10)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;

        return true;
    }


    public Collection<ApplicationEntity> getApplicationsById() {
        return applicationsById;
    }

    public void setApplicationsById(Collection<ApplicationEntity> applicationsById) {
        this.applicationsById = applicationsById;
    }


    public Collection<RateFactorResultEntity> getRateFactorResultsById() {
        return rateFactorResultsById;
    }

    public void setRateFactorResultsById(Collection<RateFactorResultEntity> rateFactorResultsById) {
        this.rateFactorResultsById = rateFactorResultsById;
    }

    public Collection<SessionsEntity> getSessionsById() {
        return sessionsById;
    }

    public void setSessionsById(Collection<SessionsEntity> sessionsById) {
        this.sessionsById = sessionsById;
    }


    public Collection<StatementEntity> getStatementsById() {
        return statementsById;
    }

    public void setStatementsById(Collection<StatementEntity> statementsById) {
        this.statementsById = statementsById;
    }
}
