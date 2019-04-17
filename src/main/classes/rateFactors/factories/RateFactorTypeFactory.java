package rateFactors.factories;

import rateFactors.RateFactorType;
import rateFactors.types.ExamType;
import rateFactors.types.SchoolCertificateType;
import selectionCommittee.Subject;


import java.util.HashMap;
import java.util.Map;

public class RateFactorTypeFactory {

    private static RateFactorTypeFactory instance = null;

    public static RateFactorTypeFactory getInstance() {

        if (instance != null) {
            return instance;
        }


        instance = new RateFactorTypeFactory();
        return instance;

    }


    private Map<String, RateFactorType> types;


    protected RateFactorTypeFactory() {
        types = new HashMap<>();
    }


    public RateFactorType createExamType(Subject subject) {

        String key = ExamType.TYPE_PREFIX + ExamType.TYPE_SEPARATOR + subject.getLabel();

        if (types.containsKey(key)) {
            return  types.get(key);
        }

        RateFactorType type = new ExamType(subject);
        types.put(key, type);

        return type;
    }


    public RateFactorType createSchoolCertificateType() {

        if (types.containsKey(SchoolCertificateType.TYPE_PREFIX)) {
            return  types.get(SchoolCertificateType.TYPE_PREFIX);
        }

        RateFactorType type = new SchoolCertificateType();
        types.put(SchoolCertificateType.TYPE_PREFIX, type);

        return type;


    }

}
