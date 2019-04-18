package commands.university;

import commands.Command;
import common.ServiceProvider;
import common.exceptions.GuardException;
import json.JsonComponent;
import selectionCommittee.SelectionCommittee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PostApplicationsCommand implements Command {


    @Override
    public JsonComponent execute(HttpServletRequest req, HttpServletResponse resp) throws GuardException {
        SelectionCommittee selectionCommittee = ServiceProvider.getInstance().getSelectionCommittee();

        String token = req.getParameter("token");
        String facultyId = req.getParameter("facultyId");

        if (token == null) {
            throw new GuardException("Token missed");
        }

        if (facultyId == null) {
            throw new GuardException("Faculty id missed");
        }

        try {
            return selectionCommittee.createApplication(token, Long.valueOf(facultyId));
        } catch (GuardException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuardException("Something went wrong");
        }

    }
}
