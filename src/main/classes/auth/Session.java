package auth;

import dataAccess.DBAccessFactory;
import user.User;

public abstract class Session {


    Long userId;
    String token;
    String type;


    public Session(Long userId, String token, String type) {
        this.userId = userId;
        this.token = token;
        this.type = type;
    }


    public abstract User getUser();


    public static Session createSession(Long userId, String token, String type) {

        try {
            if (type.equals(EnrolleeSession.ENROLLEE_TYPE)) {

                return new EnrolleeSession(
                        DBAccessFactory.getInstance().getDAOFactory().getEnrolleeDAO().get(userId),
                        token
                );
            } else if(type.equals(AdministratorSession.ADMIN_TYPE)) {
                // TODO: implement Admin session
                return new AdministratorSession(
                        null,
                        token
                );
            }

        } catch (Exception e) {
                e.printStackTrace();
        }

        return null;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
