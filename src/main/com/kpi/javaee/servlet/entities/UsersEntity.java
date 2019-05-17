package com.kpi.javaee.servlet.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users", schema = "public", catalog = "selectioncommittee")
public class UsersEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String surname;
    private String lastname;
    private String login;
    private String password;
    private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "usersByIdUser")
    private Collection<ApplicationsEntity> applicationsById;

    @JsonIgnore
    @OneToMany(mappedBy = "usersByIdUser")
    private Collection<RateFactorResultsEntity> rateFactorResultsById;

    @JsonIgnore
    @OneToMany(mappedBy = "usersByIdUser")
    private Collection<SessionsEntity> sessionsById;

    @JsonIgnore
    @OneToMany(mappedBy = "usersByIdEnrollee")
    private Collection<StatementsEntity> statementsById;


    public UsersEntity() {
    }

    @JsonCreator
    public UsersEntity(
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(Role.parseFromString(role));
    }

    @Basic
    @Column(name = "password", nullable = false, length = 60)
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
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

        UsersEntity that = (UsersEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }


    public Collection<ApplicationsEntity> getApplicationsById() {
        return applicationsById;
    }

    public void setApplicationsById(Collection<ApplicationsEntity> applicationsById) {
        this.applicationsById = applicationsById;
    }


    public Collection<RateFactorResultsEntity> getRateFactorResultsById() {
        return rateFactorResultsById;
    }

    public void setRateFactorResultsById(Collection<RateFactorResultsEntity> rateFactorResultsById) {
        this.rateFactorResultsById = rateFactorResultsById;
    }

    public Collection<SessionsEntity> getSessionsById() {
        return sessionsById;
    }

    public void setSessionsById(Collection<SessionsEntity> sessionsById) {
        this.sessionsById = sessionsById;
    }


    public Collection<StatementsEntity> getStatementsById() {
        return statementsById;
    }

    public void setStatementsById(Collection<StatementsEntity> statementsById) {
        this.statementsById = statementsById;
    }
}
