package rateFactors.results;

import rateFactors.RateFactorResult;
import rateFactors.RateFactorType;
import rateFactors.factories.RateFactorTypeFactory;

public class SchoolCertificateResult extends RateFactorResult {

    public SchoolCertificateResult(float result) {
        super(result);
    }

    @Override
    public RateFactorType getType() {
        return RateFactorTypeFactory.getInstance().createSchoolCertificateType();
    }
}
