package com.kpi.javaee.servlet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "statements", schema = "public", catalog = "selectioncommittee")
public class StatementsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private double rating;
    private Timestamp cDate;

    public StatementsEntity() {
    }

    public StatementsEntity(double rating, Timestamp cDate, FacultiesEntity facultiesByIdFaculty, UsersEntity usersByIdEnrollee, SelectionRoundsEntity selectionRoundsByIdSelectionRound) {
        this.rating = rating;
        this.cDate = cDate;
        this.facultiesByIdFaculty = facultiesByIdFaculty;
        this.usersByIdEnrollee = usersByIdEnrollee;
        this.selectionRoundsByIdSelectionRound = selectionRoundsByIdSelectionRound;
    }

    @ManyToOne
    @JoinColumn(name = "id_faculty", referencedColumnName = "id", nullable = false)
    private FacultiesEntity facultiesByIdFaculty;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_enrollee", referencedColumnName = "id", nullable = false)
    private UsersEntity usersByIdEnrollee;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_selection_round", referencedColumnName = "id", nullable = false)
    private SelectionRoundsEntity selectionRoundsByIdSelectionRound;

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

        StatementsEntity that = (StatementsEntity) o;

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


    public UsersEntity getUsersByIdEnrollee() {
        return usersByIdEnrollee;
    }

    public void setUsersByIdEnrollee(UsersEntity usersByIdEnrollee) {
        this.usersByIdEnrollee = usersByIdEnrollee;
    }


    public SelectionRoundsEntity getSelectionRoundsByIdSelectionRound() {
        return selectionRoundsByIdSelectionRound;
    }

    public void setSelectionRoundsByIdSelectionRound(SelectionRoundsEntity selectionRoundsByIdSelectionRound) {
        this.selectionRoundsByIdSelectionRound = selectionRoundsByIdSelectionRound;
    }
}
