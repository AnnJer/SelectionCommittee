package commands.university;

import commands.RestCommand;
import common.ResponseWriterUtil;
import common.ServiceProvider;
import common.exceptions.GuardException;
import json.JsonArray;
import json.JsonComponent;
import json.JsonUtil;
import rateFactors.RateFactorResult;
import rateFactors.results.ExamResult;
import rateFactors.results.SchoolCertificateResult;
import selectionCommittee.Faculty;
import selectionCommittee.Subject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class RateFactorResultsCommand extends RestCommand {

    @Override
    protected JsonComponent doPost(HttpServletRequest req, HttpServletResponse resp) throws GuardException {


        try {

            Long id = Long.valueOf((String) req.getAttribute("id"));

            String examsStr = req.getParameter("exams");

            if (examsStr == null) {
                throw new GuardException("Exam results are missed");
            }

            List<RateFactorResult> results = new ArrayList<>();

            for (String examStr: examsStr.split(",")) {

                String[] parts = examStr.split("-");

                float result = Float.valueOf(parts[0]);
                String subject = parts[1];

                results.add(new ExamResult(result, new Subject(subject)));
            }

            String schoolCertificate = req.getParameter("schoolcertificate");

            if (schoolCertificate == null) {
                throw new GuardException("School certificate are missed");
            }

            results.add(new SchoolCertificateResult(Float.valueOf(schoolCertificate)));

            if (results.size() < 1) {
                throw new GuardException("Results are missed");
            }

            return ServiceProvider.getInstance().getSelectionCommittee().updateRateFactorResults(results, id);

        } catch (Exception e) {
            throw new GuardException("Something go wrong", ResponseWriterUtil.SERVER_ERROR);
        }

    }
}
