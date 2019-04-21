package commands.university;

import commands.Command;
import common.ResponseWriterUtil;
import common.ServiceProvider;
import common.exceptions.GuardException;
import json.JsonArray;
import json.JsonComponent;
import json.JsonUtil;
import selectionCommittee.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetStatementsCommand implements Command {

    @Override
    public JsonComponent execute(HttpServletRequest req, HttpServletResponse resp) throws GuardException {

        try {

            String filterBy = req.getParameter("filterBy");
            String filterId = req.getParameter("filterId");

            String selectionRoundId = req.getParameter("selectionRoundId");

            List<Statement> statements = null;

            if (filterBy != null) {

                if (filterId == null) {
                    throw new GuardException("filterId is Empty");
                }

                if (filterBy.equals("faculty")) {

                    if (selectionRoundId == null) {
                        throw new GuardException("selectionRoundId is Empty");
                    }

                    statements = ServiceProvider.getInstance().getSelectionCommittee().getStatementByFaculty(
                            Long.parseLong(filterId), Long.parseLong(selectionRoundId)
                    );
                } else {
                    statements = ServiceProvider.getInstance().getSelectionCommittee().getStatementByEnrollee(
                            Long.parseLong(filterId)
                    );
                }

            } else {
                statements =  ServiceProvider.getInstance().getSelectionCommittee().getAllStatements();
            }

            if (statements == null) {
                return JsonUtil.array();
            }

            JsonArray answer = JsonUtil.array();

            for (Statement statement: statements) {
                answer.addValue(statement.toJson());
            }

            return answer;

        } catch (GuardException e) {
            throw e;
        } catch (Exception e) {
            throw new GuardException("Something go wrong", ResponseWriterUtil.SERVER_ERROR);
        }
    }

}
