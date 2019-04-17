package user;

import json.JsonObject;
import json.JsonUtil;
import auth.EnrolleeSession;
import auth.Session;
import dataAccess.Crypto;
import rateFactors.RateFactorResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Enrollee extends User{


    static final String USER_TYPE = "enrollee";


    protected List<RateFactorResult> examResults;
    protected RateFactorResult schoolCertificate;

    public Enrollee(Long id, String name, String lastname, String surname, String login) {
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

    @Override
    public UserRoles getRole() {
        return UserRoles.ENROLLEE;
    }

    @Override
    public JsonObject toJson() {

        JsonObject json = super.toJson();

        json.addValue("rateFactors", JsonUtil.array(new ArrayList<>() {
            {
                for (RateFactorResult result: getRateFactors()
                     ) {

                    if (result != null) {
                        add(result.toJson());
                    }
                }
            }
        }));

        json.addValue("userType", JsonUtil.string(USER_TYPE));

        return json;
    }
}
