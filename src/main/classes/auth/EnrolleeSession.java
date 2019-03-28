package auth;

import user.Enrollee;

public class EnrolleeSession extends Session {

    final static String SESSION_TYPE = "enrollee";

    Enrollee enrollee;

    public EnrolleeSession(Enrollee enrollee, String token) {
        super(enrollee.getId(), token, SESSION_TYPE);
        this.enrollee = enrollee;
    }


    @Override
    public Enrollee getUser() {
        return enrollee;
    }

}
