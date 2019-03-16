package user;

import auth.AdministratorSession;
import auth.Session;
import dataAccess.Crypto;

public class Administrator extends User{


    public Administrator(long id, String name, String lastname, String surname, String login) {
        super(id, name, lastname, surname, login);
    }

    @Override
    public Session createSession() {
        return new AdministratorSession(this, Crypto.createToken(this));
    }
}
