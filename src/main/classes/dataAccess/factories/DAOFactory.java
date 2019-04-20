package dataAccess.factories;

import dataAccess.dao.*;

public interface DAOFactory {

    SubjectDAO getSubjectDAO() throws Exception;
    RateFactorResultDAO getRateFactorResultDAO() throws Exception;
    UserDAO getUserDAO() throws Exception;
    SelectionRoundDAO getSelectionRoundDAO() throws Exception;
    SessionDAO getSessionDAO() throws Exception;
    FacultyDAO getFacultyDAO() throws Exception;
    ApplicationsDAO getApplicationDAO() throws Exception;
    StatementDAO getStatementDAO() throws Exception;

}
