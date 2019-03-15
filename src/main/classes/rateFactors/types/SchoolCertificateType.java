package rateFactors.types;

import rateFactors.RateFactorType;

public class SchoolCertificateType extends RateFactorType {

    public final static String TYPE_PREFIX = "schoolcertificate";

    @Override
    public String getType() {
        return TYPE_PREFIX;
    }

}
