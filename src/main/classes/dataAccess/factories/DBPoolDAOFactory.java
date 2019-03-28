package dataAccess.factories;

import dataAccess.dao.*;
import rateFactors.factories.RateFactorsFactory;
import university.factories.SelectionRoundFactory;

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
    public EnrolleeDAO getEnrolleeDAO() throws Exception {
        return new EnrolleeDAO(ds.getConnection());
    }

    @Override
    public SelectionRoundDAO getSelectionRoundDAO() throws Exception {
        return new SelectionRoundDAO(ds.getConnection(), new SelectionRoundFactory(), RateFactorsFactory.getInstance());
    }

    @Override
    public SessionDAO getSessionDAO() throws Exception {
        return new SessionDAO(ds.getConnection());
    }
}
