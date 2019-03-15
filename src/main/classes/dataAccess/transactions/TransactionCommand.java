package dataAccess.transactions;

import dataAccess.factories.DAOFactory;

public interface  TransactionCommand {
    void execute(DAOFactory factory) throws Exception;
}
