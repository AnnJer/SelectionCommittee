package dispatcher;

import commands.Command;
import commands.auth.GetUserCommand;
import commands.auth.SignInCommand;
import commands.auth.SignOutCommand;
import commands.university.*;
import common.ResponseWriterUtil;
import common.Router;
import common.exceptions.GuardException;
import json.JsonComponent;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class DispatcherServlet extends HttpServlet {


    @Override
    public void init() throws ServletException {
        super.init();

        Router router = Router.getInstance();


        router.add("/sign_in", new SignInCommand());
        router.add("/sign_out", new SignOutCommand());
        router.add("/sessions/{token}", new GetUserCommand());


        router.add("/faculties", new FacultiesCommand());
        router.add("/faculties/{id}", new ConcreteFacultyCommand());

        router.add("/subjects", new SubjectCommand());

        // ? byUserId , byFacultyId
        router.add("/applications", new ApplicationsCommand());
        router.add("/applications/{id}", new ConcreteFacultyCommand());


        router.add("/rate_factors/results/{id}", new RateFactorResultsCommand());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setAttribute("requestType", RequestTypes.POST);
        processRequest(req, resp);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setAttribute("requestType", RequestTypes.GET);
        processRequest(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("requestType", RequestTypes.DELETE);
        processRequest(req, resp);
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("requestType", RequestTypes.PUT);
        processRequest(req, resp);
    }


    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("requestType", RequestTypes.OPTIONS);
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        resp.setContentType("application/json");

        // TODO: declare config file with allowed domains for CORS
        resp.setHeader("Access-Control-Allow-Origin", "*");
//        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");

        String path = req.getPathInfo();

        if (path == null) {
            ResponseWriterUtil.error(resp, "Not found", ResponseWriterUtil.NOT_FOUND);
            return;
        }


        JsonComponent answer;

        try {
            Command command = Router.getInstance().route(path, req);

            if (command == null) {
                ResponseWriterUtil.error(resp, "Not found", ResponseWriterUtil.NOT_FOUND);
                return;
            }

            answer = command.execute(req, resp);

        } catch (GuardException e) {
            ResponseWriterUtil.error(
                    resp, e.toJson(),
                    (e.getErrorCode() != null) ?
                            e.getErrorCode() : ResponseWriterUtil.UNPROCESSABLE_ENTITY
            );
            return;
        }


        if (answer == null) {
            ResponseWriterUtil.error(resp, "Something go wrong", ResponseWriterUtil.SERVER_ERROR);
        }


        ResponseWriterUtil.write(resp, answer, ResponseWriterUtil.OK);
    }
}
