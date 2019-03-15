package university;

import rateFactors.RateFactorCoefficient;
import rateFactors.RateFactorResult;
import user.Enrollee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SelectionRound {

    protected Long id;

    protected Date startDate;
    protected Date endDate;

    protected List<RateFactorCoefficient> requiredExams;
    protected RateFactorCoefficient schoolCertificate;
    protected int selectionPlan;


    public SelectionRound(Date startDate, Date endDate, List<RateFactorCoefficient> requiredExams, RateFactorCoefficient schoolCertificate, int selectionPlan) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.requiredExams = requiredExams;
        this.schoolCertificate = schoolCertificate;
        this.selectionPlan = selectionPlan;
    }

    protected List<RateFactorCoefficient> getRateFactorCoefficients() {
        List<RateFactorCoefficient> coefs = new ArrayList<RateFactorCoefficient>();

        coefs.addAll(requiredExams);
        coefs.add(schoolCertificate);

        return coefs;
    }



    public boolean isCanApply(Enrollee enrollee) {

        List<RateFactorResult> results = enrollee.getRateFactors();

        for (RateFactorCoefficient coef: getRateFactorCoefficients()
        ) {
            if (!isHaveSuitable(results, coef)) {
                return false;
            }
        }

        return true;
    }

    protected boolean isHaveSuitable(List<RateFactorResult> results, RateFactorCoefficient coefficient) {
        return findSuitableResult(results, coefficient) != null;
    }


    protected RateFactorResult findSuitableResult(List<RateFactorResult> results, RateFactorCoefficient coefficient) {
        for (RateFactorResult result: results
        ) {

            if (result.isSuitable(coefficient)) {
                return result;
            }
        }

        return null;
    }


    public float calcRating(Enrollee enrollee) {

        float rating = 0;

        List<RateFactorResult> results = enrollee.getRateFactors();

        for (RateFactorCoefficient coefficient: getRateFactorCoefficients()
        ) {

            RateFactorResult result = findSuitableResult(results, coefficient);
            if (result != null) {
                rating += coefficient.getCoefficient() * result.getResult();
            }

        }

        return rating;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<RateFactorCoefficient> getRequiredExams() {
        return requiredExams;
    }

    public void setRequiredExams(List<RateFactorCoefficient> requiredExams) {
        this.requiredExams = requiredExams;
    }

    public RateFactorCoefficient getSchoolCertificate() {
        return schoolCertificate;
    }

    public void setSchoolCertificate(RateFactorCoefficient schoolCertificate) {
        this.schoolCertificate = schoolCertificate;
    }

    public int getSelectionPlan() {
        return selectionPlan;
    }

    public void setSelectionPlan(int selectionPlan) {
        this.selectionPlan = selectionPlan;
    }
}
