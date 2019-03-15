package university.factories;

import rateFactors.RateFactorCoefficient;
import rateFactors.coefficients.SchoolCertificateCoefficient;
import rateFactors.factories.RateFactorsFactory;
import rateFactors.types.SchoolCertificateType;
import university.SelectionRound;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SelectionRoundFactory {

    public SelectionRound createSelectionRound(
            int selectionPlan, Date startDate, Date endDate, List<RateFactorCoefficient> coefficients
    ) {

        RateFactorCoefficient schoolCertificate = new SchoolCertificateCoefficient(0);
        List<RateFactorCoefficient> requiredExams = new ArrayList<>();


        for (RateFactorCoefficient coef: coefficients){

            if (coef.getType().getType().equals(SchoolCertificateType.TYPE_PREFIX)) {
                schoolCertificate = coef;
                continue;
            }

            requiredExams.add(coef);
        }


        return new SelectionRound(
                startDate, endDate, requiredExams, schoolCertificate, selectionPlan
        );

    }

}
