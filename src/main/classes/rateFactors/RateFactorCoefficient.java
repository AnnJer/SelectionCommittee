package rateFactors;

import json.JsonObject;
import json.JsonSerializable;
import json.JsonUtil;

import java.util.HashMap;

public abstract class RateFactorCoefficient implements JsonSerializable {

    protected Long id;
    protected float coefficient;

    public RateFactorCoefficient(Long id, float coefficient) {
        this.id = id;
        this.coefficient = coefficient;
    }

    public RateFactorCoefficient(float coefficient) {
        this.coefficient = coefficient;
    }


    public float getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(float coefficient) {
        this.coefficient = coefficient;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public abstract RateFactorType getType();

    public boolean isSuitable(RateFactorResult result) {
        return getType().equals(result.getType());
    }


    @Override
    public JsonObject toJson() {
        return JsonUtil.object(new HashMap<>() {
            {
                put("type", JsonUtil.string(getType().getType()));
                put("coefficient", JsonUtil.number(coefficient));
            }
        });
    }
}
