package commands.university;

import commands.RestCommand;
import common.ResponseWriterUtil;
import common.ServiceProvider;
import common.exceptions.GuardException;
import json.JsonComponent;
import json.JsonUtil;
import selectionCommittee.Faculty;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GetConcreteFacultyCommand extends RestCommand {

    @Override
    public JsonComponent doGet(HttpServletRequest req, HttpServletResponse resp) throws GuardException {
        try {

            Long id = Long.valueOf((String) req.getAttribute("id"));

            Faculty faculty = ServiceProvider.getInstance().getSelectionCommittee().getFacultyById(id);

            if (faculty == null) {
                return JsonUtil.object();
            }

            return faculty.toJson();

        } catch (GuardException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuardException("Something go wrong", ResponseWriterUtil.SERVER_ERROR);
        }
    }
}
