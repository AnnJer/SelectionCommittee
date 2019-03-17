package dataAccess.transactions;

import dataAccess.factories.DAOFactory;

import java.io.Closeable;
import java.sql.SQLException;

public abstract class Transaction implements Closeable {


    protected boolean isCommitted = false;
    protected Exception exception = null;
    protected TransactionCommand command;

    protected DAOFactory factory;

    protected abstract void begin() throws Exception;
    protected abstract void rollback() throws Exception;
    protected abstract void commit() throws Exception;


    public Transaction(DAOFactory factory) {
        this.factory = factory;
    }


    public void setCommand(TransactionCommand command) {
        this.command = command;
    }

    public void execute() throws Exception {

        if (command == null) {
            throw new NullPointerException("Transaction command is not defined");
        }

        try {
            begin();
            command.execute(factory);
            commit();
            isCommitted = true;
        } catch (Exception e) {
            exception = e;
            rollback();
        }
    }


    public boolean isCommitted() {
        return isCommitted;
    }

    public Exception getException() {
        return exception;
    }
}
