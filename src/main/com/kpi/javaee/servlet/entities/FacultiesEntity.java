package com.kpi.javaee.servlet.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "faculties", schema = "public", catalog = "selectioncommittee")
public class FacultiesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String label;

    @ManyToOne
    @JoinColumn(name = "id_selection_round")
    private SelectionRoundsEntity selectionRound;

    @JsonIgnore
    @OneToMany(mappedBy = "facultiesByIdFaculty")
    private Collection<ApplicationsEntity> applicationsById;


    @JsonIgnore
    @OneToMany(mappedBy = "facultiesByIdFaculty")
    private Collection<StatementsEntity> statementsById;

    public FacultiesEntity() {
    }

    @JsonCreator
    public FacultiesEntity(
            @JsonProperty("id") int id,
            @JsonProperty("label") String label,
            @JsonProperty("selectionRound") SelectionRoundsEntity selectionRound
    ) {
        this.id = id;
        this.label = label;
        this.selectionRound = selectionRound;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "label", nullable = false, length = 50)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public SelectionRoundsEntity getSelectionRound() {
        return selectionRound;
    }

    public void setSelectionRound(SelectionRoundsEntity selectionRound) {
        this.selectionRound = selectionRound;
    }

    public Collection<ApplicationsEntity> getApplicationsById() {
        return applicationsById;
    }

    public void setApplicationsById(Collection<ApplicationsEntity> applicationsById) {
        this.applicationsById = applicationsById;
    }


    public Collection<StatementsEntity> getStatementsById() {
        return statementsById;
    }

    public void setStatementsById(Collection<StatementsEntity> statementsById) {
        this.statementsById = statementsById;
    }
}
