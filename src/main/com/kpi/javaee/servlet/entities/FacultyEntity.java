package com.kpi.javaee.servlet.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "faculties", schema = "public", catalog = "selectioncommittee")
public class FacultyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String label;

    @ManyToOne
    @JoinColumn(name = "id_selection_round")
    private SelectionRoundEntity selectionRound;

    @JsonIgnore
    @OneToMany(mappedBy = "facultiesByIdFaculty")
    private Collection<ApplicationEntity> applicationsById;


    @JsonIgnore
    @OneToMany(mappedBy = "facultiesByIdFaculty")
    private Collection<StatementEntity> statementsById;

    public FacultyEntity() {
    }

    @JsonCreator
    public FacultyEntity(
            @JsonProperty("id") Long id,
            @JsonProperty("label") String label,
            @JsonProperty("selectionRound") SelectionRoundEntity selectionRound
    ) {
        this.id = id;
        this.label = label;
        this.selectionRound = selectionRound;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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


    public SelectionRoundEntity getSelectionRound() {
        return selectionRound;
    }

    public void setSelectionRound(SelectionRoundEntity selectionRound) {
        this.selectionRound = selectionRound;
    }

    public Collection<ApplicationEntity> getApplicationsById() {
        return applicationsById;
    }

    public void setApplicationsById(Collection<ApplicationEntity> applicationsById) {
        this.applicationsById = applicationsById;
    }


    public Collection<StatementEntity> getStatementsById() {
        return statementsById;
    }

    public void setStatementsById(Collection<StatementEntity> statementsById) {
        this.statementsById = statementsById;
    }
}
