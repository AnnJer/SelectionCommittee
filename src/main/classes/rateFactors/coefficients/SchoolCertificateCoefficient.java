package rateFactors.coefficients;

import rateFactors.RateFactorCoefficient;
import rateFactors.RateFactorType;
import rateFactors.factories.RateFactorTypeFactory;

public class SchoolCertificateCoefficient extends RateFactorCoefficient {


    public SchoolCertificateCoefficient(float coefficient) {
        super(coefficient);
    }

    @Override
    public RateFactorType getType() {
        return RateFactorTypeFactory.getInstance().createSchoolCertificateType();
    }
}
