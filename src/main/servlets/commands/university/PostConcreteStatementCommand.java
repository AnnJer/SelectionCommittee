package commands.university;

import commands.Command;
import common.ResponseWriterUtil;
import common.ServiceProvider;
import common.exceptions.GuardException;
import json.JsonComponent;
import json.JsonUtil;
import selectionCommittee.Faculty;
import selectionCommittee.Statement;
import user.Enrollee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PostConcreteStatementCommand implements Command {

    @Override
    public JsonComponent execute(HttpServletRequest req, HttpServletResponse resp) throws GuardException {
        try {

            String enrolleeIdStr = req.getParameter("enrolleeIdList");

            if (enrolleeIdStr == null) {
                throw new GuardException("Enrollee id list missed.", ResponseWriterUtil.SERVER_ERROR);
            }

            String[] enrolleeIdListStr = enrolleeIdStr.split(",");


            String facultyIdStr = req.getParameter("facultyId");

            if (facultyIdStr == null) {
                throw new GuardException("Faculty id list missed.", ResponseWriterUtil.SERVER_ERROR);
            }

            String selectionRoundIdStr = req.getParameter("selectionRoundId");

            if (selectionRoundIdStr == null) {
                throw new GuardException("Selection round id list missed.", ResponseWriterUtil.SERVER_ERROR);
            }


            List<Statement> statements = new ArrayList<>();

            for (String s: enrolleeIdListStr) {
                statements.add(
                        new Statement(
                                null,
                                new Faculty(Long.parseLong(facultyIdStr), null, null, null),
                                new Enrollee(Long.parseLong(s), null, null, null, null),
                                Long.parseLong(selectionRoundIdStr),
                                null,
                                null
                        )
                );
            }

            ServiceProvider.getInstance().getSelectionCommittee().saveStatements(statements);

            return JsonUtil.object(new HashMap<>() {
                {
                    put("status", JsonUtil.string("Successfully updated"));
                }
            });

        } catch (GuardException e) {
            throw e;
        } catch (Exception e) {
            throw new GuardException("Something go wrong", ResponseWriterUtil.SERVER_ERROR);
        }
    }

}
