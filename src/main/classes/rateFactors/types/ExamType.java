package rateFactors.types;

import rateFactors.RateFactorType;
import university.Subject;

public class ExamType extends RateFactorType {


    public final static String TYPE_PREFIX = "exam";

    private Subject subject;


    public ExamType(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String getType() {
        return TYPE_PREFIX + TYPE_SEPARATOR + subject.getLabel();
    }




}
