package commands.university;

import commands.Command;
import common.ResponseWriterUtil;
import common.ServiceProvider;
import common.exceptions.GuardException;
import json.JsonComponent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteConcreteApplicationCommand implements Command {


    @Override
    public JsonComponent execute(HttpServletRequest req, HttpServletResponse resp) throws GuardException {
        try {

            long id = Long.valueOf((String) req.getAttribute("id"));

            return ServiceProvider.getInstance().getSelectionCommittee().deleteApplication(id);

        } catch (GuardException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuardException("Something go wrong", ResponseWriterUtil.SERVER_ERROR);
        }
    }

}
