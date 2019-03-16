package rateFactors.factories;

import rateFactors.RateFactorCoefficient;
import rateFactors.RateFactorResult;
import rateFactors.RateFactorType;
import rateFactors.coefficients.ExamCoefficient;
import rateFactors.coefficients.SchoolCertificateCoefficient;
import rateFactors.results.ExamResult;
import rateFactors.results.SchoolCertificateResult;
import rateFactors.types.ExamType;
import rateFactors.types.SchoolCertificateType;
import university.factories.SubjectFactory;

public class RateFactorsFactory {

    private static RateFactorsFactory instance = null;

    public static RateFactorsFactory getInstance() {

        if (instance != null) {
            return instance;
        }


        instance = new RateFactorsFactory();
        return instance;

    }



    protected RateFactorsFactory () {
        subjectFactory = SubjectFactory.getInstance();
    }



    private SubjectFactory subjectFactory;


    public RateFactorCoefficient createCoefficient(float coefficient, String type) {
        if (type.equals(SchoolCertificateType.TYPE_PREFIX)) {
            return new SchoolCertificateCoefficient(coefficient);
        } else if (type.contains(ExamType.TYPE_PREFIX + ExamType.TYPE_SEPARATOR)) {
            return new ExamCoefficient(
                    coefficient,
                    subjectFactory.createSubject(type.split(RateFactorType.TYPE_SEPARATOR)[1])
            );
        }

        return null;
    }


    public RateFactorResult createResult(float result, String type) {
        if (type.equals(SchoolCertificateType.TYPE_PREFIX)) {
            return new SchoolCertificateResult(result);
        } else if (type.contains(ExamType.TYPE_PREFIX + ExamType.TYPE_SEPARATOR)) {
            return new ExamResult(
                    result, subjectFactory.createSubject(type.split(RateFactorType.TYPE_SEPARATOR)[1])
            );
        }

        return null;
    }


}
