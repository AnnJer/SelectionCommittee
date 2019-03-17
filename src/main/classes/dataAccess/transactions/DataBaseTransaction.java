package dataAccess.transactions;

import dataAccess.factories.DAOFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DataBaseTransaction extends Transaction {

    private Connection conn;

    public DataBaseTransaction(DAOFactory factory, Connection conn) throws SQLException {
        super(factory);
        this.conn = conn;
    }

    @Override
    protected void begin() throws SQLException {
        conn.setAutoCommit(false);
    }

    @Override
    protected void rollback() throws SQLException {
        conn.rollback();
        conn.setAutoCommit(true);
    }

    @Override
    protected void commit() throws SQLException {
        conn.commit();
        conn.setAutoCommit(true);
    }

    @Override
    public void close() throws IOException {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
