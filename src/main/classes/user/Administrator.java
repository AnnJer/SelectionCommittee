package user;

import json.JsonObject;
import auth.AdministratorSession;
import auth.Session;
import dataAccess.Crypto;
import json.JsonSerializable;
import json.JsonUtil;

public class Administrator extends User {


    public Administrator(long id, String name, String lastname, String surname, String login) {
        super(id, name, lastname, surname, login);
    }

    @Override
    public Session createSession() {
        return new AdministratorSession(this, Crypto.createToken(this));
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = super.toJson();

        json.addValue("userType", JsonUtil.string(getRole().toString()));

        return json;
    }


    @Override
    public UserRoles getRole() {
        return UserRoles.ADMINISTRATOR;
    }



}
