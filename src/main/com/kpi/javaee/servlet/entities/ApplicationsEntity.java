package com.kpi.javaee.servlet.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "applications", schema = "public", catalog = "selectioncommittee")
public class ApplicationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private double rating;
    private Timestamp cDate;

    @JsonProperty("faculty")
    @ManyToOne
    @JoinColumn(name = "id_faculty", referencedColumnName = "id", nullable = false)
    private FacultiesEntity facultiesByIdFaculty;

    @JsonProperty("user")
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    private UsersEntity usersByIdUser;


    public ApplicationsEntity() {
    }

    @JsonCreator
    public ApplicationsEntity(
            @JsonProperty("id") int id,
            @JsonProperty("rating") double rating,
            @JsonProperty("cDate") Timestamp cDate,
            @JsonProperty("faculty") FacultiesEntity facultiesByIdFaculty,
            @JsonProperty("user") UsersEntity usersByIdUser
    ) {
        this.id = id;
        this.rating = rating;
        this.cDate = cDate;
        this.facultiesByIdFaculty = facultiesByIdFaculty;
        this.usersByIdUser = usersByIdUser;
    }

    public ApplicationsEntity(double rating, Timestamp cDate, FacultiesEntity facultiesByIdFaculty, UsersEntity usersByIdUser) {
        this.rating = rating;
        this.cDate = cDate;
        this.facultiesByIdFaculty = facultiesByIdFaculty;
        this.usersByIdUser = usersByIdUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "rating", nullable = false, precision = 0)
    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Basic
    @Column(name = "c_date", nullable = false)
    public Timestamp getcDate() {
        return cDate;
    }

    public void setcDate(Timestamp cDate) {
        this.cDate = cDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationsEntity that = (ApplicationsEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.rating, rating) != 0) return false;
        if (cDate != null ? !cDate.equals(that.cDate) : that.cDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (cDate != null ? cDate.hashCode() : 0);
        return result;
    }


    public FacultiesEntity getFacultiesByIdFaculty() {
        return facultiesByIdFaculty;
    }

    public void setFacultiesByIdFaculty(FacultiesEntity facultiesByIdFaculty) {
        this.facultiesByIdFaculty = facultiesByIdFaculty;
    }


    public UsersEntity getUsersByIdUser() {
        return usersByIdUser;
    }

    public void setUsersByIdUser(UsersEntity usersByIdUser) {
        this.usersByIdUser = usersByIdUser;
    }
}
