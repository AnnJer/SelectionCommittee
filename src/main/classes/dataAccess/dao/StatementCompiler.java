package dataAccess.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementCompiler {

    void prepare(PreparedStatement st) throws SQLException;

}
