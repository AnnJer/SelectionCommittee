package auth;

import dataAccess.DBAccessFactory;
import dataAccess.dao.UserDAO;
import json.JsonObject;
import json.JsonSerializable;
import json.JsonUtil;
import user.Administrator;
import user.Enrollee;
import user.User;

import java.util.HashMap;

public abstract class Session implements JsonSerializable {


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

        try (
                UserDAO userDAO = DBAccessFactory.getInstance().getDAOFactory().getUserDAO()
                ) {
            if (type.equals(EnrolleeSession.SESSION_TYPE)) {

                return new EnrolleeSession(
                        (Enrollee) userDAO.get(userId),
                        token
                );
            } else if(type.equals(AdministratorSession.SESSION_TYPE)) {
                // TODO: implement Admin session
                return new AdministratorSession(
                        (Administrator) userDAO.get(userId),
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


    @Override
    public JsonObject toJson() {
        return JsonUtil.object(new HashMap<>() {
            {
                put("token", JsonUtil.string(token));
                put("type", JsonUtil.string(type));
                put("user", getUser().toJson());
            }
        });
    }

}
