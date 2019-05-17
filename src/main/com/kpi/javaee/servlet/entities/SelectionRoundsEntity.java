package com.kpi.javaee.servlet.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "selection_rounds", schema = "public", catalog = "selectioncommittee")
public class SelectionRoundsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int selectionPlan;
    private Date startDate;
    private Date endDate;

    @JsonProperty("rateFactorCoefficients")
    @OneToMany(mappedBy = "selectionRoundsByIdSelectionRound")
    private Collection<RateFactorCoefficientsEntity> rateFactorCoefficientsById;

    @JsonIgnore
    @OneToMany(mappedBy = "selectionRoundsByIdSelectionRound")
    private Collection<StatementsEntity> statementsById;

    public SelectionRoundsEntity() {
    }

    @JsonCreator
    public SelectionRoundsEntity(
            @JsonProperty("id") int id,
            @JsonProperty("selectionPlan") int selectionPlan,
            @JsonProperty("startDate") Date startDate,
            @JsonProperty("endDate") Date endDate,
            @JsonProperty("rateFactorCoefficients") Collection<RateFactorCoefficientsEntity> rateFactorCoefficientsById
    ) {
        this.id = id;
        this.selectionPlan = selectionPlan;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rateFactorCoefficientsById = rateFactorCoefficientsById;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

        SelectionRoundsEntity that = (SelectionRoundsEntity) o;

        if (id != that.id) return false;
        if (selectionPlan != that.selectionPlan) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + selectionPlan;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }


    public Collection<RateFactorCoefficientsEntity> getRateFactorCoefficientsById() {
        return rateFactorCoefficientsById;
    }

    public void setRateFactorCoefficientsById(Collection<RateFactorCoefficientsEntity> rateFactorCoefficientsById) {
        this.rateFactorCoefficientsById = rateFactorCoefficientsById;
    }


    public Collection<StatementsEntity> getStatementsById() {
        return statementsById;
    }

    public void setStatementsById(Collection<StatementsEntity> statementsById) {
        this.statementsById = statementsById;
    }
}
