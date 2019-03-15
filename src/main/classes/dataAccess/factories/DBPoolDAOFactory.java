package dataAccess.factories;

import dataAccess.DAO.EnrolleeDAO;
import dataAccess.DAO.RateFactorResultDAO;
import dataAccess.DAO.SubjectDAO;
import dataAccess.factories.DAOFactory;

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
        return new RateFactorResultDAO(ds.getConnection());
    }

    @Override
    public EnrolleeDAO getEnrolleeDAO() throws Exception {
        return new EnrolleeDAO(ds.getConnection());
    }
}
