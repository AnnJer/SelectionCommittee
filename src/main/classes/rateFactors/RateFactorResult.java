package rateFactors;

public abstract class RateFactorResult {

    protected Long id;
    protected float result;


    public RateFactorResult(Long id, float result) {
        this.id = id;
        this.result = result;
    }

    public RateFactorResult(float result) {
        this.result = result;
    }


    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }


    public abstract RateFactorType getType();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSuitable(RateFactorCoefficient coefficient) {
        return getType().equals(coefficient.getType());
    }
}
