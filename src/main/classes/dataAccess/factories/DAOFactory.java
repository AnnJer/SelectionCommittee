package dataAccess.factories;

import dataAccess.DAO.EnrolleeDAO;
import dataAccess.DAO.RateFactorResultDAO;
import dataAccess.DAO.SelectionRoundDAO;
import dataAccess.DAO.SubjectDAO;
import user.Enrollee;

import java.sql.SQLException;

public interface DAOFactory {

    SubjectDAO getSubjectDAO() throws Exception;
    RateFactorResultDAO getRateFactorResultDAO() throws Exception;
    EnrolleeDAO getEnrolleeDAO() throws Exception;
    SelectionRoundDAO getSelectionRoundDAO() throws Exception;

}
