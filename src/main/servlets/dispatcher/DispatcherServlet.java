package dispatcher;

import auth.Auth;
import commands.Command;
import commands.auth.GetUserCommand;
import commands.auth.SignInCommand;
import common.exceptions.GuardException;
import json.JsonComponent;
import json.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {


    private Map<String, Command> routes;
    private Auth auth;

    @Override
    public void init() throws ServletException {
        super.init();

        auth = new Auth();

        routes = new HashMap<>();


        routes.put("/sign_in", new SignInCommand(auth));
        routes.put("/sign_out", new SignInCommand(auth));
        routes.put("/get_user", new GetUserCommand(auth));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }



    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        resp.setContentType("application/json");


        String path = req.getPathInfo();

        PrintWriter out = resp.getWriter();


        if (path == null) {
            out.write(JsonUtil.object(new HashMap<>() {
                {
                    put("error", JsonUtil.string("Invalid path"));
                }
            }).encode());
        }


        JsonComponent answer = null;

        for (String route: routes.keySet()) {

            if (route.equals(path)) {
                try {
                    answer = routes.get(route).execute(req, resp);
                } catch (GuardException e) {

                    out.write(JsonUtil.object(new HashMap<>() {
                        {
                            put("error", e.toJson());
                        }
                    }).encode());

                    return;
                }
            }
        }


        if (answer == null) {
            out.write(JsonUtil.object(new HashMap<>() {
                {
                    put("error", JsonUtil.string("Invalid path"));
                }
            }).encode());
        }



        out.write(answer.encode());
    }
}
