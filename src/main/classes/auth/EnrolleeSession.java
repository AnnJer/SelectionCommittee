package auth;

import user.Enrollee;
import user.User;

public class EnrolleeSession extends Session {

    final static String ENROLLEE_TYPE = "enrollee";

    Enrollee enrollee;

    public EnrolleeSession(Enrollee enrollee, String token) {
        super(enrollee.getId(), token, ENROLLEE_TYPE);
        this.enrollee = enrollee;
    }


    @Override
    public Enrollee getUser() {
        return enrollee;
    }

}
