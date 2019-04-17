package commands.university;

import commands.RestCommand;
import common.ResponseWriterUtil;
import common.ServiceProvider;
import common.exceptions.GuardException;
import json.JsonArray;
import json.JsonComponent;
import json.JsonUtil;
import selectionCommittee.Subject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class SubjectCommand extends RestCommand {

    @Override
    protected JsonComponent doGet(HttpServletRequest req, HttpServletResponse resp) throws GuardException {
        try {
            List<Subject> subjects = ServiceProvider.getInstance().getSelectionCommittee().getAllSubjects();

            JsonArray subjectsArr = JsonUtil.array();

            for (Subject subject: subjects) {
                subjectsArr.addValue(subject.toJson());
            }

            return subjectsArr;

        } catch (Exception e) {
            throw new GuardException("Something go wrong", ResponseWriterUtil.SERVER_ERROR);
        }
    }
}
