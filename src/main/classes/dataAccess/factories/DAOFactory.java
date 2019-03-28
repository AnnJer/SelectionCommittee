package dataAccess.factories;

import dataAccess.dao.*;

public interface DAOFactory {

    SubjectDAO getSubjectDAO() throws Exception;
    RateFactorResultDAO getRateFactorResultDAO() throws Exception;
    EnrolleeDAO getEnrolleeDAO() throws Exception;
    SelectionRoundDAO getSelectionRoundDAO() throws Exception;
    SessionDAO getSessionDAO() throws Exception;

}
