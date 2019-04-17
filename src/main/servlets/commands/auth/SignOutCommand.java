package commands.auth;

import auth.Auth;
import commands.Command;
import common.ResponseWriterUtil;
import common.ServiceProvider;
import common.exceptions.GuardException;
import json.JsonComponent;
import json.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignOutCommand implements Command {


    private Auth auth;


    public SignOutCommand() {
        this.auth = ServiceProvider.getInstance().getAuth();
    }

    @Override
    public JsonComponent execute(HttpServletRequest req, HttpServletResponse resp) throws GuardException {

        String token = req.getParameter("token");

        if (token == null) {
            throw new GuardException("Token is missed");
        }

        try {
            auth.signOut(token);
        } catch (Exception e) {
            throw new GuardException("Something go wrong", ResponseWriterUtil.SERVER_ERROR);
        }

        return JsonUtil.object();
    }
}
