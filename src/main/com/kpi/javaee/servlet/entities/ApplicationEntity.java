package com.kpi.javaee.servlet.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "applications", schema = "public", catalog = "selectioncommittee")
public class ApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double rating;
    private Timestamp cDate;

    @JsonProperty("faculty")
    @ManyToOne
    @JoinColumn(name = "id_faculty", referencedColumnName = "id", nullable = false)
    private FacultyEntity facultiesByIdFaculty;

    @JsonProperty("user")
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    private UserEntity usersByIdUser;


    public ApplicationEntity() {
    }

    @JsonCreator
    public ApplicationEntity(
            @JsonProperty("id") Long id,
            @JsonProperty("rating") double rating,
            @JsonProperty("cDate") Timestamp cDate,
            @JsonProperty("faculty") FacultyEntity facultiesByIdFaculty,
            @JsonProperty("user") UserEntity usersByIdUser
    ) {
        this.id = id;
        this.rating = rating;
        this.cDate = cDate;
        this.facultiesByIdFaculty = facultiesByIdFaculty;
        this.usersByIdUser = usersByIdUser;
    }

    public ApplicationEntity(double rating, Timestamp cDate, FacultyEntity facultiesByIdFaculty, UserEntity usersByIdUser) {
        this.rating = rating;
        this.cDate = cDate;
        this.facultiesByIdFaculty = facultiesByIdFaculty;
        this.usersByIdUser = usersByIdUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

        ApplicationEntity that = (ApplicationEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.rating, rating) != 0) return false;
        if (cDate != null ? !cDate.equals(that.cDate) : that.cDate != null) return false;

        return true;
    }


    public FacultyEntity getFacultiesByIdFaculty() {
        return facultiesByIdFaculty;
    }

    public void setFacultiesByIdFaculty(FacultyEntity facultiesByIdFaculty) {
        this.facultiesByIdFaculty = facultiesByIdFaculty;
    }


    public UserEntity getUsersByIdUser() {
        return usersByIdUser;
    }

    public void setUsersByIdUser(UserEntity usersByIdUser) {
        this.usersByIdUser = usersByIdUser;
    }
}
