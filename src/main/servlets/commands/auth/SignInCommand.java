package commands.auth;

import auth.Auth;
import auth.Session;
import commands.RestCommand;
import common.ServiceProvider;
import common.exceptions.GuardException;
import dataAccess.Crypto;
import json.JsonComponent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInCommand extends RestCommand {

    private Auth auth;

    public SignInCommand() {
        this.auth = ServiceProvider.getInstance().getAuth();
    }

    @Override
    public JsonComponent doPost(HttpServletRequest req, HttpServletResponse resp) throws GuardException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || password == null) {
            throw new GuardException("Empty password or login");
        }

//            if (password.length() < 6) {
//                throw new GuardException("Password is too short");
//            }

        Session session = null;
        try {

            password = new String(Crypto.encodePassword(login.getBytes(), password.getBytes()));
            session = auth.signIn(login, password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuardException("Something go wrong");
        }

        if (session == null) {
            throw new GuardException("Invalid password or login");
        }

        return session.toJson();
    }
}
