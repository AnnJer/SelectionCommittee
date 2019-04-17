package dataAccess.factories;

import dataAccess.dao.*;
import rateFactors.factories.RateFactorsFactory;
import selectionCommittee.factories.ApplicationManagerFactory;
import selectionCommittee.factories.SelectionRoundFactory;

import java.sql.Connection;

public class DBSingleDAOFactory implements DAOFactory {

    private Connection conn;


    public DBSingleDAOFactory(Connection conn) {
        this.conn = conn;
    }

    @Override
    public SubjectDAO getSubjectDAO() {
        return new SubjectDAO(conn);
    }

    @Override
    public RateFactorResultDAO getRateFactorResultDAO() throws Exception {
        return new RateFactorResultDAO(conn, RateFactorsFactory.getInstance());
    }

    @Override
    public EnrolleeDAO getEnrolleeDAO() throws Exception {
        return new EnrolleeDAO(conn);
    }

    @Override
    public SelectionRoundDAO getSelectionRoundDAO() throws Exception {
        return new SelectionRoundDAO(conn, new SelectionRoundFactory(), RateFactorsFactory.getInstance());
    }

    @Override
    public SessionDAO getSessionDAO() throws Exception {
        return new SessionDAO(conn);
    }

    @Override
    public FacultyDAO getFacultyDAO() throws Exception {
        return new FacultyDAO(conn, new ApplicationManagerFactory());
    }

    @Override
    public ApplicationsDAO getApplicationDAO() throws Exception {
        return new ApplicationsDAO(conn);
    }
}
