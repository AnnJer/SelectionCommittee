package user;

import auth.EnrolleeSession;
import auth.Session;
import dataAccess.Crypto;
import rateFactors.RateFactorResult;

import java.util.ArrayList;
import java.util.List;

public class Enrollee extends User{

    protected List<RateFactorResult> examResults;
    protected RateFactorResult schoolCertificate;

    public Enrollee(long id, String name, String lastname, String surname, String login) {
        super(id, name, lastname, surname, login);
    }


    public List<RateFactorResult> getExamResults() {
        return examResults;
    }

    public RateFactorResult getSchoolCertificate() {
        return schoolCertificate;
    }

    public void setExamResults(List<RateFactorResult> examResults) {
        this.examResults = examResults;
    }

    public void setSchoolCertificate(RateFactorResult schoolCertificate) {
        this.schoolCertificate = schoolCertificate;
    }

    public List<RateFactorResult> getRateFactors() {
        List<RateFactorResult> results = new ArrayList<>();

        results.addAll(examResults);
        results.add(schoolCertificate);

        return results;
    }

    @Override
    public Session createSession() {
        return new EnrolleeSession(this, Crypto.createToken(this));
    }
}
