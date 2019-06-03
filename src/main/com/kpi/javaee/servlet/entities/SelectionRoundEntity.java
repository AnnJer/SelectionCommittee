package com.kpi.javaee.servlet.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "selection_rounds", schema = "public", catalog = "selectioncommittee")
public class SelectionRoundEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int selectionPlan;
    private Date startDate;
    private Date endDate;

    @JsonProperty("rateFactorCoefficients")
    @OneToMany(mappedBy = "selectionRoundsByIdSelectionRound")
    private Collection<RateFactorCoefficientEntity> rateFactorCoefficientsById;

    @JsonIgnore
    @OneToMany(mappedBy = "selectionRoundsByIdSelectionRound")
    private Collection<StatementEntity> statementsById;

    public SelectionRoundEntity() {
    }

    @JsonCreator
    public SelectionRoundEntity(
            @JsonProperty("id") Long id,
            @JsonProperty("selectionPlan") int selectionPlan,
            @JsonProperty("startDate") Date startDate,
            @JsonProperty("endDate") Date endDate,
            @JsonProperty("rateFactorCoefficients") Collection<RateFactorCoefficientEntity> rateFactorCoefficientsById
    ) {
        this.id = id;
        this.selectionPlan = selectionPlan;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rateFactorCoefficientsById = rateFactorCoefficientsById;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "selection_plan", nullable = false)
    public int getSelectionPlan() {
        return selectionPlan;
    }

    public void setSelectionPlan(int selectionPlan) {
        this.selectionPlan = selectionPlan;
    }

    @Basic
    @Column(name = "start_date", nullable = false)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = false)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SelectionRoundEntity that = (SelectionRoundEntity) o;

        if (id != that.id) return false;
        if (selectionPlan != that.selectionPlan) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;

        return true;
    }


    public Collection<RateFactorCoefficientEntity> getRateFactorCoefficientsById() {
        return rateFactorCoefficientsById;
    }

    public void setRateFactorCoefficientsById(Collection<RateFactorCoefficientEntity> rateFactorCoefficientsById) {
        this.rateFactorCoefficientsById = rateFactorCoefficientsById;
    }


    public Collection<StatementEntity> getStatementsById() {
        return statementsById;
    }

    public void setStatementsById(Collection<StatementEntity> statementsById) {
        this.statementsById = statementsById;
    }
}
