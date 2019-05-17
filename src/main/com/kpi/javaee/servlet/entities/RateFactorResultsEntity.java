package com.kpi.javaee.servlet.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "rate_factor_results", schema = "public", catalog = "selectioncommittee")
public class RateFactorResultsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private double result;
    private String type;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_user")
    private UsersEntity usersByIdUser;


    public RateFactorResultsEntity() {
    }

    public RateFactorResultsEntity(
            double result,
            String type,
            UsersEntity usersByIdUser
    ) {
        this.result = result;
        this.type = type;
        this.usersByIdUser = usersByIdUser;
    }

    @JsonCreator
    public RateFactorResultsEntity(
            @JsonProperty("result") double result,
            @JsonProperty("type") String type
    ) {
        this.result = result;
        this.type = type;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

        RateFactorResultsEntity that = (RateFactorResultsEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.result, result) != 0) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1;
        long temp;
        result1 = id;
        temp = Double.doubleToLongBits(result);
        result1 = 31 * result1 + (int) (temp ^ (temp >>> 32));
        result1 = 31 * result1 + (type != null ? type.hashCode() : 0);
        return result1;
    }


    public UsersEntity getUsersByIdUser() {
        return usersByIdUser;
    }

    public void setUsersByIdUser(UsersEntity usersByIdUser) {
        this.usersByIdUser = usersByIdUser;
    }
}
