package rateFactors.coefficients;

import rateFactors.RateFactorCoefficient;
import rateFactors.RateFactorType;
import rateFactors.factories.RateFactorTypeFactory;
import selectionCommittee.Subject;

public class ExamCoefficient extends RateFactorCoefficient {


    private Subject subject;

    public ExamCoefficient(float coefficient, Subject subject) {
        super(coefficient);
        this.subject = subject;
    }

    @Override
    public RateFactorType getType() {
        return RateFactorTypeFactory.getInstance().createExamType(subject);
    }
}
