package commands.university;

import commands.Command;
import commands.RestCommand;
import common.ServiceProvider;
import common.exceptions.GuardException;
import json.JsonArray;
import json.JsonComponent;
import json.JsonUtil;
import selectionCommittee.Application;
import selectionCommittee.SelectionCommittee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class GetApplicationsCommand implements Command {

    @Override
    public JsonComponent execute(HttpServletRequest req, HttpServletResponse resp) throws GuardException {
        SelectionCommittee selectionCommittee = ServiceProvider.getInstance().getSelectionCommittee();

        String userId = req.getParameter("byUserId");
        String facultyId = req.getParameter("byFacultyId");

        try {

            List<Application> applications;

            if (userId != null) {
                applications = new ArrayList<>();
                applications.add(selectionCommittee.getApplicationByUser(Long.valueOf(userId)));
            } else if (facultyId != null) {
                applications = selectionCommittee.getApplicationByFaculty(Long.valueOf(facultyId));
            } else {
                applications = selectionCommittee.getAllApplications();
            }

            JsonArray answer = JsonUtil.array();


            if (applications == null) {
                return answer;
            }

            for (Application application : applications) {
                if (application == null) {
                    continue;
                }
                answer.addValue(application.toJson());
            }

            return answer;

        } catch (GuardException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuardException("Something went wrong");
        }
    }
}