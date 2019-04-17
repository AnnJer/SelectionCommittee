package commands.university;

import commands.Command;
import commands.RestCommand;
import common.ResponseWriterUtil;
import common.ServiceProvider;
import common.exceptions.GuardException;
import json.JsonArray;
import json.JsonComponent;
import json.JsonUtil;
import selectionCommittee.Faculty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class FacultiesCommand extends RestCommand {

    @Override
    public JsonComponent doGet(HttpServletRequest req, HttpServletResponse resp) throws GuardException {

        try {
            List<Faculty> faculties = ServiceProvider.getInstance().getSelectionCommittee().getAllFaculties();

            JsonArray facultiesArr = JsonUtil.array();

            for (Faculty faculty: faculties) {
                facultiesArr.addValue(faculty.toJson());
            }

            return facultiesArr;

        } catch (Exception e) {
            throw new GuardException("Something go wrong", ResponseWriterUtil.SERVER_ERROR);
        }
    }

}
