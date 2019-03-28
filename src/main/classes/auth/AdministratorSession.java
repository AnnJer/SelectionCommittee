package auth;

import user.Administrator;

public class AdministratorSession extends Session {

    final static String SESSION_TYPE = "admin";

    private Administrator admin;

    public AdministratorSession(Administrator admin, String token) {
        super(admin.getId(), token, SESSION_TYPE);
        this.admin = admin;
    }


    @Override
    public Administrator getUser() {
        return admin;
    }
}
