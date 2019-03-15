package dataAccess;

import dataAccess.factories.DAOFactory;
import dataAccess.transactions.Transaction;

public interface DataAccessFactory {

    DAOFactory getDAOFactory() throws Exception;
    Transaction getTransaction() throws Exception;

}
