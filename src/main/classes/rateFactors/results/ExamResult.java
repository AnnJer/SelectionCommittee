package rateFactors.results;

import rateFactors.RateFactorResult;
import rateFactors.RateFactorType;
import rateFactors.factories.RateFactorTypeFactory;
import university.Subject;


public class ExamResult extends RateFactorResult {

    private Subject subject;

    public ExamResult(float result, Subject subject) {
        super(result);
        this.subject = subject;
    }


    @Override
    public RateFactorType getType() {
        return RateFactorTypeFactory.getInstance().createExamType(subject);
    }
}
