package dataAccess;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

import dataAccess.factories.DAOFactory;
import dataAccess.factories.DBPoolDAOFactory;
import dataAccess.factories.DBSingleDAOFactory;
import dataAccess.transactions.DataBaseTransaction;
import dataAccess.transactions.Transaction;
import org.apache.tomcat.jdbc.pool.DataSource;


public class DBAccessFactory implements DataAccessFactory {


    private static DBAccessFactory instance;

    private DAOFactory df;
    private DataSource ds;



    public static DBAccessFactory getInstance() {

        if (instance != null) {
            return instance;
        }

        instance = new DBAccessFactory();
        return instance;
    }




    protected DBAccessFactory() {

        // TODO: Move DataSource configuration into XML context
        ds = new DataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/selectioncommittee");
        ds.setUsername("postgres");
        ds.setPassword("ghjnfnbg");
        ds.setInitialSize(1);
        ds.setMaxActive(10);
        ds.setMaxIdle(5);
        ds.setMinIdle(2);
        ds.setValidationQuery("Select 1;");
    }




    @Override
    public DAOFactory getDAOFactory() {

        if (df != null) {
            return df;
        }

        df = new DBPoolDAOFactory(ds);
        return df;
    }

    @Override
    public Transaction getTransaction() throws SQLException {
        Connection conn = ds.getConnection();
        return new DataBaseTransaction(new DBSingleDAOFactory(conn), conn) ;
    }

}
