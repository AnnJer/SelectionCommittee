package com.kpi.javaee.servlet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "statements", schema = "public", catalog = "selectioncommittee")
public class StatementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double rating;
    private Timestamp cDate;

    public StatementEntity() {
    }

    public StatementEntity(double rating, Timestamp cDate, FacultyEntity facultiesByIdFaculty, UserEntity usersByIdEnrollee, SelectionRoundEntity selectionRoundsByIdSelectionRound) {
        this.rating = rating;
        this.cDate = cDate;
        this.facultiesByIdFaculty = facultiesByIdFaculty;
        this.usersByIdEnrollee = usersByIdEnrollee;
        this.selectionRoundsByIdSelectionRound = selectionRoundsByIdSelectionRound;
    }

    @ManyToOne
    @JoinColumn(name = "id_faculty", referencedColumnName = "id", nullable = false)
    private FacultyEntity facultiesByIdFaculty;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_enrollee", referencedColumnName = "id", nullable = false)
    private UserEntity usersByIdEnrollee;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_selection_round", referencedColumnName = "id", nullable = false)
    private SelectionRoundEntity selectionRoundsByIdSelectionRound;

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

        StatementEntity that = (StatementEntity) o;

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


    public UserEntity getUsersByIdEnrollee() {
        return usersByIdEnrollee;
    }

    public void setUsersByIdEnrollee(UserEntity usersByIdEnrollee) {
        this.usersByIdEnrollee = usersByIdEnrollee;
    }


    public SelectionRoundEntity getSelectionRoundsByIdSelectionRound() {
        return selectionRoundsByIdSelectionRound;
    }

    public void setSelectionRoundsByIdSelectionRound(SelectionRoundEntity selectionRoundsByIdSelectionRound) {
        this.selectionRoundsByIdSelectionRound = selectionRoundsByIdSelectionRound;
    }
}
