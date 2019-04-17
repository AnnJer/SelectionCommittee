package commands.auth;

import auth.Auth;
import auth.Session;
import commands.Command;
import common.ServiceProvider;
import common.exceptions.GuardException;
import common.viewEvents.factories.ViewEventFactory;
import json.JsonComponent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetUserCommand implements Command {

    private Auth auth;

    public GetUserCommand() {
        this.auth = ServiceProvider.getInstance().getAuth();
    }

    @Override
    public JsonComponent execute(HttpServletRequest req, HttpServletResponse resp) throws GuardException {

        String token = (String) req.getAttribute("token");

        if (token == null) {
            throw new GuardException("Token is missed");
        }

        Session session = null;
        try {
            session = auth.getSession(token);
        } catch (Exception e) {
            throw new GuardException("Something go wrong");
        }

        if (session == null) {
            throw new GuardException("No sessions with this token", ViewEventFactory.getInstance().redirect("/sign_in"));
        }

        return session.getUser().toJson();
    }
}
