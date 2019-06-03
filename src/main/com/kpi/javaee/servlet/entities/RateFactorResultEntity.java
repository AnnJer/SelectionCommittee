package com.kpi.javaee.servlet.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "rate_factor_results", schema = "public", catalog = "selectioncommittee")
public class RateFactorResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double result;
    private String type;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity usersByIdUser;


    public RateFactorResultEntity() {
    }

    public RateFactorResultEntity(
            double result,
            String type,
            UserEntity usersByIdUser
    ) {
        this.result = result;
        this.type = type;
        this.usersByIdUser = usersByIdUser;
    }

    @JsonCreator
    public RateFactorResultEntity(
            @JsonProperty("result") double result,
            @JsonProperty("type") String type
    ) {
        this.result = result;
        this.type = type;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "result", nullable = false, precision = 0)
    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 50)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RateFactorResultEntity that = (RateFactorResultEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.result, result) != 0) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    public UserEntity getUsersByIdUser() {
        return usersByIdUser;
    }

    public void setUsersByIdUser(UserEntity usersByIdUser) {
        this.usersByIdUser = usersByIdUser;
    }
}
