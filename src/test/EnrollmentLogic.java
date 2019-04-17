import org.junit.Assert;
import org.junit.Test;
import rateFactors.RateFactorCoefficient;
import rateFactors.RateFactorResult;
import rateFactors.coefficients.ExamCoefficient;
import rateFactors.coefficients.SchoolCertificateCoefficient;
import rateFactors.results.ExamResult;
import rateFactors.results.SchoolCertificateResult;
import selectionCommittee.*;
import user.Enrollee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnrollmentLogic {


    private Enrollee createEnrollee() {
        Enrollee enrollee = new Enrollee(1L, "Bob", "Bobson", "Kevin", "dd@h.j");

        List<RateFactorResult> exams = new ArrayList<>();
        exams.add(new ExamResult(191, new Subject("Math")));
        exams.add(new ExamResult(190, new Subject("Ukrainian")));
        exams.add(new ExamResult(196, new Subject("Physics")));
        exams.add(new ExamResult(197, new Subject("Chemistry")));

        enrollee.setExamResults(exams);
        enrollee.setSchoolCertificate(new SchoolCertificateResult(10.8f));

        return enrollee;
    }

    private Enrollee createEnrolleeWithOneExam() {
        Enrollee enrollee = new Enrollee(1L, "Bob", "Bobson", "Kevin", "dd@h.j");

        List<RateFactorResult> exams = new ArrayList<>();
        exams.add(new ExamResult(190, new Subject("Ukrainian")));

        enrollee.setExamResults(exams);
        enrollee.setSchoolCertificate(new SchoolCertificateResult(10.8f));

        return enrollee;
    }

    private SelectionRound createSelectionRound() {

        List<RateFactorCoefficient> exams = new ArrayList<>();
        exams.add(new ExamCoefficient(0.4f, new Subject("Math")));
        exams.add(new ExamCoefficient(0.3f, new Subject("Physics")));
        exams.add(new ExamCoefficient(0.2f, new Subject("Ukrainian")));


        return  new SelectionRound(
                new Date(),
                new Date(),
                exams,
                new SchoolCertificateCoefficient(0.1f),
                10
                );
    }


    @Test
    public void testApplicationChecking() {

        SelectionRound selectionRound = createSelectionRound();

        Faculty faculty = new Faculty(1L, "H&P", selectionRound, ApplicationManager.getInstance());

        Assert.assertTrue(faculty.isCanApply(createEnrollee()));
        Assert.assertFalse(faculty.isCanApply(createEnrolleeWithOneExam()));


        Faculty facultyWithNoSelectionRound = new Faculty(
                2L, "", null, ApplicationManager.getInstance()
        );

        Assert.assertFalse(facultyWithNoSelectionRound.isCanApply(createEnrollee()));
        Assert.assertFalse(facultyWithNoSelectionRound.isCanApply(createEnrolleeWithOneExam()));

    }

    @Test
    public void testRatingCalculation() {

        SelectionRound selectionRound = createSelectionRound();

        Assert.assertTrue(
                selectionRound.calcRating(createEnrollee()) == 191*0.4f + 190*0.2f + 196*0.3f + 10.8f*0.1f
        );
    }


    @Test
    public void testJDBC() {
        try {




        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
