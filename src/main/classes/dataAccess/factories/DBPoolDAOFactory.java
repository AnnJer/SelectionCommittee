package dataAccess.factories;

import dataAccess.dao.*;
import rateFactors.factories.RateFactorsFactory;
import selectionCommittee.factories.ApplicationManagerFactory;
import selectionCommittee.factories.SelectionRoundFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DBPoolDAOFactory implements DAOFactory {

    private DataSource ds;

    public DBPoolDAOFactory(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public SubjectDAO getSubjectDAO() throws SQLException {
        return new SubjectDAO(ds.getConnection());
    }

    @Override
    public RateFactorResultDAO getRateFactorResultDAO() throws Exception {
        return new RateFactorResultDAO(ds.getConnection(), RateFactorsFactory.getInstance());
    }

    @Override
    public UserDAO getUserDAO() throws Exception {
        return new UserDAO(ds.getConnection());
    }

    @Override
    public SelectionRoundDAO getSelectionRoundDAO() throws Exception {
        return new SelectionRoundDAO(ds.getConnection(), new SelectionRoundFactory(), RateFactorsFactory.getInstance());
    }

    @Override
    public SessionDAO getSessionDAO() throws Exception {
        return new SessionDAO(ds.getConnection());
    }

    @Override
    public FacultyDAO getFacultyDAO() throws Exception {
        return new FacultyDAO(ds.getConnection(), new ApplicationManagerFactory());
    }

    @Override
    public ApplicationsDAO getApplicationDAO() throws Exception {
        return new ApplicationsDAO(ds.getConnection());
    }

    @Override
    public StatementDAO getStatementDAO() throws Exception {
        return new StatementDAO(ds.getConnection());
    }


}
