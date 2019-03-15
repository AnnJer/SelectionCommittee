package dataAccess.factories;

import dataAccess.DAO.EnrolleeDAO;
import dataAccess.DAO.RateFactorResultDAO;
import dataAccess.DAO.SubjectDAO;
import dataAccess.factories.DAOFactory;

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
        return new RateFactorResultDAO(conn);
    }

    @Override
    public EnrolleeDAO getEnrolleeDAO() throws Exception {
        return new EnrolleeDAO(conn);
    }
}
