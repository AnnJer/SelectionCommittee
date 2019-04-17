package commands.university;

import commands.RestCommand;
import common.ResponseWriterUtil;
import common.ServiceProvider;
import common.exceptions.GuardException;
import json.JsonComponent;
import json.JsonUtil;
import selectionCommittee.Application;
import selectionCommittee.Faculty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConcreteApplicationCommand extends RestCommand {
    @Override
    protected JsonComponent doGet(HttpServletRequest req, HttpServletResponse resp) throws GuardException {
        try {

            long id = Long.valueOf((String) req.getAttribute("id"));

            Application application = ServiceProvider.getInstance().getSelectionCommittee().getApplicationById(id);

            return application.toJson();

        } catch (Exception e) {
            throw new GuardException("Something go wrong", ResponseWriterUtil.SERVER_ERROR);
        }
    }


    @Override
    protected JsonComponent doDelete(HttpServletRequest req, HttpServletResponse resp) throws GuardException {
        try {

            long id = Long.valueOf((String) req.getAttribute("id"));

            return ServiceProvider.getInstance().getSelectionCommittee().deleteApplication(id);

        } catch (Exception e) {
            throw new GuardException("Something go wrong", ResponseWriterUtil.SERVER_ERROR);
        }
    }
}
