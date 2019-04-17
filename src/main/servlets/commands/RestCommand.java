package commands;

import common.ResponseWriterUtil;
import common.exceptions.GuardException;
import dispatcher.RequestTypes;
import json.JsonComponent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RestCommand implements Command {

    @Override
    public JsonComponent execute(HttpServletRequest req, HttpServletResponse resp) throws GuardException {

        RequestTypes requestType = (RequestTypes)req.getAttribute("requestType");

        if (requestType == RequestTypes.GET) {
            return doGet(req, resp);
        } else if (requestType == RequestTypes.POST) {
            return doPost(req, resp);
        } else if (requestType == RequestTypes.DELETE) {
            return doDelete(req, resp);
        } else {
            return doPut(req, resp);
        }
    }


    protected JsonComponent doGet(HttpServletRequest req, HttpServletResponse resp) throws GuardException {
        throw new GuardException("Method not implemented", ResponseWriterUtil.METHOD_NOT_ALLOWED);
    }

    protected JsonComponent doPost(HttpServletRequest req, HttpServletResponse resp) throws GuardException {
        throw new GuardException("Method not implemented", ResponseWriterUtil.METHOD_NOT_ALLOWED);
    }

    protected JsonComponent doDelete(HttpServletRequest req, HttpServletResponse resp) throws GuardException {
        throw new GuardException("Method not implemented", ResponseWriterUtil.METHOD_NOT_ALLOWED);
    }

    protected JsonComponent doPut(HttpServletRequest req, HttpServletResponse resp) throws GuardException {
        throw new GuardException("Method not implemented", ResponseWriterUtil.METHOD_NOT_ALLOWED);
    }


}
