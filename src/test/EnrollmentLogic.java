import dataAccess.DBAccessFactory;
import org.junit.Assert;
import org.junit.Test;
import rateFactors.RateFactorCoefficient;
import rateFactors.RateFactorResult;
import rateFactors.coefficients.ExamCoefficient;
import rateFactors.coefficients.SchoolCertificateCoefficient;
import rateFactors.results.ExamResult;
import rateFactors.results.SchoolCertificateResult;
import university.*;
import university.factories.SubjectFactory;
import user.Enrollee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnrollmentLogic {


    private Enrollee createEnrollee() {
        Enrollee enrollee = new Enrollee(1, "Bob", "Bobson", "Kevin", "dd@h.j");

        List<RateFactorResult> exams = new ArrayList<>();
        exams.add(new ExamResult(191, SubjectFactory.getInstance().createSubject("Math")));
        exams.add(new ExamResult(190, SubjectFactory.getInstance().createSubject("Ukrainian")));
        exams.add(new ExamResult(196, SubjectFactory.getInstance().createSubject("Physics")));
        exams.add(new ExamResult(197, SubjectFactory.getInstance().createSubject("Chemistry")));

        enrollee.setExamResults(exams);
        enrollee.setSchoolCertificate(new SchoolCertificateResult(10.8f));

        return enrollee;
    }


    private Enrollee createEnrolleeWithOneExam() {
        Enrollee enrollee = new Enrollee(1, "Bob", "Bobson", "Kevin", "dd@h.j");

        List<RateFactorResult> exams = new ArrayList<>();
        exams.add(new ExamResult(190, SubjectFactory.getInstance().createSubject("Ukrainian")));

        enrollee.setExamResults(exams);
        enrollee.setSchoolCertificate(new SchoolCertificateResult(10.8f));

        return enrollee;
    }


    private SelectionRound createSelectionRound() {

        List<RateFactorCoefficient> exams = new ArrayList<>();
        exams.add(new ExamCoefficient(0.4f, SubjectFactory.getInstance().createSubject("Math")));
        exams.add(new ExamCoefficient(0.3f, SubjectFactory.getInstance().createSubject("Physics")));
        exams.add(new ExamCoefficient(0.2f, SubjectFactory.getInstance().createSubject("Ukrainian")));


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

            Enrollee enr = DBAccessFactory.getInstance().getDAOFactory().getEnrolleeDAO().get(1);
            enr.setSurname("Bobrikova");

            DBAccessFactory.getInstance().getDAOFactory().getEnrolleeDAO().update(enr);


            System.out.println(DBAccessFactory.getInstance().getDAOFactory().getEnrolleeDAO().get(1).getSchoolCertificate());

            for (Enrollee e: DBAccessFactory.getInstance().getDAOFactory().getEnrolleeDAO().getAll()) {
                System.out.println(e.getName() + " " + e.getSurname());

                for (RateFactorResult r: e.getExamResults()) {
                    System.out.println(r.getId() + " " + r.getResult() + " - " + r.getType());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
