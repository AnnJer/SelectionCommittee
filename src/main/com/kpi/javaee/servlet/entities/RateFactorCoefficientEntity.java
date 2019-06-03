package com.kpi.javaee.servlet.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "rate_factor_coefficients", schema = "public", catalog = "selectioncommittee")
public class RateFactorCoefficientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double coefficient;
    private String type;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_selection_round")
    private SelectionRoundEntity selectionRoundsByIdSelectionRound;


    public RateFactorCoefficientEntity() {
    }

    @JsonCreator
    public RateFactorCoefficientEntity(
            @JsonProperty("coefficient") double coefficient,
            @JsonProperty("type") String type
    ) {
        this.coefficient = coefficient;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "coefficient", nullable = false, precision = 0)
    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
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

        RateFactorCoefficientEntity that = (RateFactorCoefficientEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.coefficient, coefficient) != 0) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }


    public SelectionRoundEntity getSelectionRoundsByIdSelectionRound() {
        return selectionRoundsByIdSelectionRound;
    }

    public void setSelectionRoundsByIdSelectionRound(SelectionRoundEntity selectionRoundsByIdSelectionRound) {
        this.selectionRoundsByIdSelectionRound = selectionRoundsByIdSelectionRound;
    }
}
