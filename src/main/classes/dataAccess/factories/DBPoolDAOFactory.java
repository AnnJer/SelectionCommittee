package dataAccess.factories;

import dataAccess.DAO.EnrolleeDAO;
import dataAccess.DAO.RateFactorResultDAO;
import dataAccess.DAO.SelectionRoundDAO;
import dataAccess.DAO.SubjectDAO;
import dataAccess.factories.DAOFactory;
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
}
