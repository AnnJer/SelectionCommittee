package selectionCommittee;

import common.ResponseWriterUtil;
import common.ServiceProvider;
import common.exceptions.GuardException;
import dataAccess.DataAccessFactory;
import dataAccess.dao.ApplicationsDAO;
import dataAccess.dao.FacultyDAO;
import dataAccess.dao.RateFactorResultDAO;
import dataAccess.dao.SubjectDAO;
import dataAccess.transactions.Transaction;
import json.JsonComponent;
import json.JsonUtil;
import rateFactors.RateFactorResult;
import user.Enrollee;
import user.User;
import user.UserRoles;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SelectionCommittee {


    private DataAccessFactory dataAccessFactory;

    public SelectionCommittee(DataAccessFactory dataAccessFactory) {
        this.dataAccessFactory = dataAccessFactory;
    }


    public List<Faculty> getAllFaculties() throws Exception {
        try (
                FacultyDAO facultyDAO = dataAccessFactory.getDAOFactory().getFacultyDAO()
                ) {
            return facultyDAO.getAll();
        }
    }

    public Faculty getFacultyById(long id) throws Exception {
        try (
                FacultyDAO facultyDAO = dataAccessFactory.getDAOFactory().getFacultyDAO()
        ) {
            return facultyDAO.get(id);
        }
    }


    public List<Subject> getAllSubjects() throws Exception {
        try (
                SubjectDAO subjectDAO = dataAccessFactory.getDAOFactory().getSubjectDAO()
        ) {
            return subjectDAO.getAll();
        }
    }


    public Application getApplicationByUser(long id) throws Exception {
        try (
                ApplicationsDAO applicationDAO = dataAccessFactory.getDAOFactory().getApplicationDAO();
        ) {
            return applicationDAO.getByUserId(id);
        }
    }


    public List<Application> getApplicationByFaculty(long id) throws Exception {
        try (
                ApplicationsDAO applicationDAO = dataAccessFactory.getDAOFactory().getApplicationDAO();
        ) {
            return applicationDAO.getByFacultyId(id);
        }
    }

    public List<Application> getAllApplications() throws Exception {
        try (
                ApplicationsDAO applicationDAO = dataAccessFactory.getDAOFactory().getApplicationDAO();
        ) {
            return applicationDAO.getAll();
        }
    }


    public Application getApplicationById(long id) throws Exception {
        try (
                ApplicationsDAO applicationDAO = dataAccessFactory.getDAOFactory().getApplicationDAO();
        ) {
            return applicationDAO.get(id);
        }
    }


    public JsonComponent deleteApplication(long id) throws Exception {
        try (
                ApplicationsDAO applicationDAO = dataAccessFactory.getDAOFactory().getApplicationDAO();
                FacultyDAO facultyDAO = dataAccessFactory.getDAOFactory().getFacultyDAO();
        ) {

            Application application = applicationDAO.get(id);
            Faculty faculty = facultyDAO.get(application.getFaculty().getId());

            if (faculty.selectionRound.getEndDate().before(new Date())) {
                throw new GuardException("Cant delete application after selection ending");
            }

            applicationDAO.delete(application);

            return JsonUtil.object(new HashMap<>() {
                {
                    put("status", JsonUtil.string("Successfully deleted"));
                }
            });
        }
    }


    public JsonComponent createApplication(String token, long facultyId) throws Exception {
        try (
                ApplicationsDAO applicationDAO = dataAccessFactory.getDAOFactory().getApplicationDAO();
        ) {

            User user = ServiceProvider.getInstance().getAuth().getSession(token).getUser();

            if (user == null) {
                throw new GuardException("Not authorized", ResponseWriterUtil.UNAUTHORIZED);
            }

            if (user.getRole() != UserRoles.ENROLLEE) {
                throw new GuardException("Permission denied", ResponseWriterUtil.FORBIDDEN);
            }

            Enrollee enrollee = (Enrollee) user;

            Faculty faculty = ServiceProvider.getInstance().getSelectionCommittee().getFacultyById(facultyId);

            float rating = faculty.selectionRound.calcRating(enrollee);

            if (faculty.selectionRound.getEndDate().before(new Date())) {
                throw new GuardException("Cant apply after selection ending");
            }

            Application application = new Application(faculty, enrollee, rating, null);

            if (!faculty.applicationManager.isValidApplication(application)) {
                throw new GuardException("You have already get application to other faculty. Only 1 application allowed.");
            }

            applicationDAO.save(application);

            return JsonUtil.object(new HashMap<>() {
                {
                    put("status", JsonUtil.string("Successfully saved"));
                }
            });
        }
    }


    public JsonComponent updateRateFactorResults(List<RateFactorResult> results, long userId) throws Exception {

        Transaction transaction = dataAccessFactory.getTransaction();

        transaction.setCommand((daoFactory) -> {

            RateFactorResultDAO rateFactorResultDAO = daoFactory.getRateFactorResultDAO();

            rateFactorResultDAO.deleteByEnrolleId(userId);
            rateFactorResultDAO.batchInsert(results, userId);

        });

        transaction.execute();

        transaction.close();

        if (!transaction.isCommitted()) {
            throw new GuardException("Something go wrond");
        }

        return JsonUtil.object(new HashMap<>() {
            {
                put("status", JsonUtil.string("Successfully saved"));
            }
        });
    }

}
